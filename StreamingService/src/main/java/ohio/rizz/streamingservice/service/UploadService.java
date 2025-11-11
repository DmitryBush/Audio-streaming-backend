package ohio.rizz.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import ohio.rizz.streamingservice.service.filesystem.FileSystemService;
import ohio.rizz.streamingservice.service.metadata.*;
import ohio.rizz.streamingservice.service.storage.StorageService;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final MetadataParserService metadataParserService;
    private final ContentTypeService contentTypeService;
    private final FileSystemService fileSystemService;
    private final StorageService storageService;

    private final ArtistService artistService;
    private final GenreService genreService;
    private final AlbumService albumService;
    private final SongService songService;

    @Transactional(rollbackOn = Exception.class)
    public SongReadDto uploadFile(MultipartFile multipartFile) {
        final SongDto song;
        File tmpSongFile = null;
        try {
            tmpSongFile = fileSystemService.createTemporalFile(multipartFile,
                                                               contentTypeService.getSuffixType(multipartFile));
            multipartFile.transferTo(tmpSongFile);
            song = metadataParserService.extractMetadataFromFile(tmpSongFile);

            File tmpArtFile = fileSystemService.createTemporalFile("art", song.albumDto().artworkDto().suffix());
            fileSystemService.copyByteArrayToFile(tmpArtFile, song.albumDto().artworkDto().binaryArray());

            var artist = artistService.getReferenceById(artistService.createArtist(song.artistDto()).id());
            var genre = genreService.getReferenceById(genreService.createGenre(song.albumDto().genreDto()).id());
            var album = albumService.getReferenceById(albumService.createAlbum(song.albumDto(), artist, genre).id());
            String songObjectReference = String.format("track/%s/audio%s",
                                                   UUID.nameUUIDFromBytes(song.name().getBytes(StandardCharsets.UTF_8)),
                                                   contentTypeService.getSuffixType(multipartFile));
            var songReadDto = songService.createSong(song, album, songObjectReference);

            storageService.saveFile(tmpSongFile, "audio", songObjectReference);
            storageService.saveFile(tmpArtFile, "art", song.albumDto().artworkDto().objectReference());
            return songReadDto;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tmpSongFile).ifPresent(file -> {
                if (!file.delete()) {
                    throw new RuntimeException("The temporary file was not deleted due to an unknown error");
                }
            });
        }
    }
}
