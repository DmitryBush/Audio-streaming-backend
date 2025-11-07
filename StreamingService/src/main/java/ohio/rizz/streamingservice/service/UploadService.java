package ohio.rizz.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.filesystem.FilesystemService;
import ohio.rizz.streamingservice.service.metadata.*;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final MetadataParserService metadataParserService;
    private final ContentTypeService contentTypeService;
    private final FilesystemService filesystemService;

    private final ArtistService artistService;
    private final GenreService genreService;
    private final AlbumService albumService;
    private final SongService songService;

    @Transactional(rollbackOn = Exception.class)
    public SongReadDto uploadFile(MultipartFile multipartFile) {
        final SongDto song;
        File tempFile = null;
        try {
            tempFile = filesystemService.createTemporalFile(multipartFile,
                                                            contentTypeService.getSuffixType(multipartFile));
            multipartFile.transferTo(tempFile);
            song = metadataParserService.extractMetadataFromFile(tempFile);

            var artist = artistService.getReferenceById(artistService.createArtist(song.artistDto()).id());
            var genre = genreService.getReferenceById(genreService.createGenre(song.albumDto().genreDto()).id());
            var album = albumService.getReferenceById(albumService.createAlbum(song.albumDto(), artist, genre).id());
            return songService.createSong(song, artist, album);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tempFile).ifPresent(file -> {
                if (!file.delete()) {
                    throw new RuntimeException("The temporary file was not deleted due to an unknown error");
                }
            });
        }

    }
}
