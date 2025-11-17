package ohio.rizz.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.dto.song.AudioMetadataDto;
import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import ohio.rizz.streamingservice.service.artist.ArtistService;
import ohio.rizz.streamingservice.service.filesystem.FileSystemService;
import ohio.rizz.streamingservice.service.genre.GenreService;
import ohio.rizz.streamingservice.service.metadata.*;
import ohio.rizz.streamingservice.service.song.AudioMetadataService;
import ohio.rizz.streamingservice.service.song.SongService;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        File tmpSongFile = fileSystemService.createTemporalFile(multipartFile,
                                                                contentTypeService.getSuffixType(multipartFile));
        File tmpArtFile = fileSystemService.createTemporalFile("art", ".tmp");;
        try {
            multipartFile.transferTo(tmpSongFile);
            final SongDto songDto = metadataParserService.extractMetadataFromFile(tmpSongFile);

            Artist artist = uploadArtistMetadata(songDto);

            Album album = null;
            album = uploadAlbum(songDto, album, artist, tmpArtFile);

            var songReadDto = songService.createSong(songDto, album);

            String songObjectReference = String.format("track/%s/audio",
                                                       UUID.nameUUIDFromBytes(songDto.name().getBytes(StandardCharsets.UTF_8)));
            metadataService.createSongMetadata(new AudioMetadataDto(songReadDto.id(), multipartFile.getSize(),
                                                                    multipartFile.getContentType(), songObjectReference));
            objectStorageService.saveFile(tmpSongFile, "audio", songObjectReference);
            if (Objects.nonNull(songDto.albumDto())) {
                if (Objects.nonNull(songDto.albumDto().artworkDto())) {
                    objectStorageService.saveFile(tmpArtFile, "art", songDto.albumDto()
                            .artworkDto().objectReference());
                }
            }
            return songReadDto;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tmpSongFile).ifPresent(file -> {
                if (!file.delete()) {
                    throw new RuntimeException("The temporary file was not deleted due to an unknown error");
                }
            });
            Optional.ofNullable(tmpArtFile).ifPresent(file -> {
                if (!file.delete()) {
                    throw new RuntimeException("The temporary file was not deleted due to an unknown error");
                }
            });
        }
    }

    private Album uploadAlbum(SongDto song, Album album, Artist artist, File tmpArtFile) {
        if (Objects.nonNull(song.albumDto())) {
            Genre genre = genreService.getReferenceById(genreService.createGenre(song.albumDto().genreDto()).id());
            album = albumService.getReferenceById(albumService.createAlbum(song.albumDto(), artist, genre).id());
            if (Objects.nonNull(song.albumDto().artworkDto())) {
                fileSystemService.copyByteArrayToFile(tmpArtFile, song.albumDto()
                                                              .artworkDto()
                                                              .binaryArray());
            }
        }
        return album;
    }

    @Nullable
    private Artist uploadArtistMetadata(SongDto song) {
        Artist artist = null;
        if (Objects.nonNull(song.artistDto())) {
            artist = artistService.getReferenceById(artistService.createArtist(song.artistDto()).id());
        }
        return artist;
    }
}
