package ohio.rizz.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;
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
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
        File tempArtFile = fileSystemService.createTemporalFile("art", ".tmp");
        SongDto songDto = null;
        Map<String, CompletableFuture<Void>> asyncTasks = new HashMap<>();
        try {
            multipartFile.transferTo(tempSongFile);
            songDto = metadataParserService.extractMetadataFromFile(tempSongFile);
            final AlbumDto albumDto = songDto.albumDto();
            final ArtistDto artistDto = songDto.artistDto();

            asyncTasks.put(BucketStreamingConstants.AUDIO.getTitle(), objectStorageService
                    .saveFileAsync(tempSongFile, "audio", songDto.objectReference()));
            asyncTasks.put(BucketStreamingConstants.ART.getTitle(), uploadArtwork(albumDto, tempArtFile));

            Artist artist = uploadArtistMetadata(artistDto);
            Album album = uploadAlbumMetadata(albumDto, artist);
            SongReadDto songReadDto = songService.createSong(songDto, album);
            metadataService.createSongMetadata(new AudioMetadataDto(songReadDto.id(), multipartFile.getSize(),
                    multipartFile.getContentType(), songDto.objectReference()));

            asyncTasks.values().forEach(CompletableFuture::join);
            return songReadDto;
        } catch (IOException | RuntimeException e) {
            log.error("Filed to upload file: {}", e.getMessage());
            asyncTasks.values().forEach(future -> {
                if (!future.isDone()) {
                    future.cancel(true);
                }
            });
            rollbackObjectStorageUpload(songDto);
            throw new RuntimeException(e);
        } finally {
            deleteTemporalFile(tempSongFile);
            deleteTemporalFile(tempArtFile);
        }
    }

    private void rollbackObjectStorageUpload(SongDto songDto) {
        Optional.ofNullable(songDto)
                .ifPresent(dto -> {
                    objectStorageService.deleteFile(BucketStreamingConstants.AUDIO.getTitle(), dto.objectReference());
                    Optional.ofNullable(songDto.albumDto())
                            .map(AlbumDto::artworkDto)
                            .ifPresent(artworkDto ->
                                    objectStorageService.deleteFile(BucketStreamingConstants.ART.getTitle(),
                                            artworkDto.objectReference()));
                });
    }

    private static void deleteTemporalFile(File file) {
        if (!file.delete()) {
            throw new RuntimeException("The temporary file was not deleted due to an unknown error");
        }
    }

    private CompletableFuture<Void> uploadArtwork(AlbumDto albumDto, File tempArtFile) {
        return Optional.ofNullable(albumDto)
                .map(AlbumDto::artworkDto)
                .map(artworkDto -> {
                    fileSystemService.copyByteArrayToFile(tempArtFile, albumDto.artworkDto().binaryArray());
                    return objectStorageService.saveFileAsync(tempArtFile, "art",
                            albumDto.artworkDto().objectReference());
                })
                .orElse(CompletableFuture.completedFuture(null));
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
}
