package ohio.rizz.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.ArtworkDto;
import ohio.rizz.streamingservice.dto.GenreDto;
import ohio.rizz.streamingservice.dto.song.AudioMetadataDto;
import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import ohio.rizz.streamingservice.service.artist.ArtistService;
import ohio.rizz.streamingservice.service.filesystem.FileSystemService;
import ohio.rizz.streamingservice.service.genre.GenreService;
import ohio.rizz.streamingservice.service.metadata.MetadataParserService;
import ohio.rizz.streamingservice.service.song.AudioMetadataService;
import ohio.rizz.streamingservice.service.song.SongService;
import ohio.rizz.streamingservice.service.storage.BucketStreamingConstants;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import ohio.rizz.streamingservice.service.storage.exception.ObjectStorageException;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {
    private final MetadataParserService metadataParserService;
    private final ContentTypeService contentTypeService;
    private final FileSystemService fileSystemService;
    private final ObjectStorageService objectStorageService;

    private final ArtistService artistService;
    private final GenreService genreService;
    private final AlbumService albumService;
    private final SongService songService;
    private final AudioMetadataService metadataService;

    @Transactional(rollbackOn = Exception.class)
    public SongReadDto uploadFile(MultipartFile multipartFile) {
        File tempSongFile = fileSystemService.createTemporalFile(multipartFile,
                contentTypeService.getSuffixType(multipartFile));
        Map<String, String> objectReferencesMap = new HashMap<>();
        Map<String, CompletableFuture<Void>> asyncTasks = new HashMap<>();
        try {
            multipartFile.transferTo(tempSongFile);
            final SongDto songDto = metadataParserService.extractMetadataFromFile(tempSongFile);

            fillObjectReferenceMap(objectReferencesMap, songDto);
            runAsyncObjectUploadTasks(asyncTasks, tempSongFile, songDto);

            SongReadDto songReadDto = createDatabaseRecords(multipartFile, songDto);

            asyncTasks.values().forEach(CompletableFuture::join);
            return songReadDto;
        } catch (IOException | CompletionException | ObjectStorageException e) {
            log.error("Filed to upload file: {}", e.getMessage());
            asyncTasks.values().forEach(future -> {
                if (!future.isDone()) {
                    future.cancel(true);
                }
            });
            objectReferencesMap.forEach(this::deleteObjectStorageUpload);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } finally {
            deleteTemporalFile(tempSongFile);
        }
    }

    private static void fillObjectReferenceMap(Map<String, String> objectReferencesMap, final SongDto songDto) {
        objectReferencesMap.put(BucketStreamingConstants.AUDIO.getTitle(), songDto.objectReference());
        objectReferencesMap.put(BucketStreamingConstants.ART.getTitle(),
                Optional.ofNullable(songDto.albumDto())
                        .map(AlbumDto::artworkDto)
                        .map(ArtworkDto::objectReference)
                        .orElse(""));
    }

    private void runAsyncObjectUploadTasks(Map<String, CompletableFuture<Void>> asyncTasks, File tempSongFile, SongDto songDto) {
        asyncTasks.put(BucketStreamingConstants.AUDIO.getTitle(), objectStorageService
                .saveFileAsync(tempSongFile, BucketStreamingConstants.AUDIO.getTitle(), songDto.objectReference()));
        asyncTasks.put(BucketStreamingConstants.ART.getTitle(), uploadArtwork(songDto.albumDto()));
    }

    private CompletableFuture<Void> uploadArtwork(AlbumDto albumDto) {
        return Optional.ofNullable(albumDto)
                .map(AlbumDto::artworkDto)
                .map(artworkDto -> objectStorageService.saveFileAsync(
                        new ByteArrayInputStream(artworkDto.binaryArray()),
                        artworkDto.binaryArray().length,
                        BucketStreamingConstants.ART.getTitle(),
                        albumDto.artworkDto().objectReference()))
                .orElse(CompletableFuture.completedFuture(null));
    }

    @NotNull
    private SongReadDto createDatabaseRecords(MultipartFile multipartFile, SongDto songDto) {
        final ArtistDto artistDto = songDto.artistDto();
        final AlbumDto albumDto = songDto.albumDto();

        Artist artist = uploadArtistMetadata(artistDto);
        Album album = uploadAlbumMetadata(albumDto, artist);
        SongReadDto songReadDto = songService.createSong(songDto, album);
        metadataService.createSongMetadata(new AudioMetadataDto(songReadDto.id(), multipartFile.getSize(),
                multipartFile.getContentType(), songDto.objectReference()));
        return songReadDto;
    }

    private Album uploadAlbumMetadata(AlbumDto albumDto, Artist artist) {
        return Optional.ofNullable(albumDto)
                .map(dto -> {
                    Genre genre = uploadGenreMetadata(dto.genreDto());
                    return albumService.createAlbum(albumDto, artist, genre);
                })
                .map(albumReadDto -> albumService.getReferenceById(albumReadDto.id()))
                .orElse(null);
    }

    private Genre uploadGenreMetadata(GenreDto genreDto) {
        return Optional.ofNullable(genreDto)
                .map(genreService::createGenre)
                .map(genreReadDto -> genreService.getReferenceById(genreReadDto.id()))
                .orElse(null);
    }

    @Nullable
    private Artist uploadArtistMetadata(ArtistDto artistDto) {
        return Optional.ofNullable(artistDto)
                .map(artistService::createArtist)
                .map(artistReadDto -> artistService.getReferenceById(artistReadDto.id()))
                .orElse(null);
    }

    private void deleteObjectStorageUpload(String bucket, String reference) {
        if (!reference.isEmpty()) {
            objectStorageService.deleteFile(bucket, reference);
        }
    }

    private static void deleteTemporalFile(File file) {
        if (!file.delete()) {
            throw new RuntimeException("The temporary file was not deleted due to an unknown error");
        }
    }
}
