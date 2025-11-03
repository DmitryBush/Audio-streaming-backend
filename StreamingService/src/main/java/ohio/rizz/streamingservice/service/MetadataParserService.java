package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.GenreDto;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataParserService {
    private final ContentTypeService contentTypeService;

    public SongDto extractMetadataFromFile(MultipartFile multipartFile) {
        AudioFileMetadata metadata = readAudioFileMetadata(multipartFile);

        var album = getAlbumDto(metadata.tag());
        var artist = getArtistDto(metadata.tag());
        return getSongDto(metadata.tag(), metadata.header(), artist, album);
    }

    private AudioFileMetadata readAudioFileMetadata(MultipartFile multipartFile) {
        AudioHeader header;
        Tag tag;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("upload_", contentTypeService.getSuffixType(multipartFile));
            multipartFile.transferTo(tempFile);

            AudioFile audioFile = AudioFileIO.read(tempFile);
            header = audioFile.getAudioHeader();
            tag = audioFile.getTag();
        } catch (ReadOnlyFileException | IOException | CannotReadException | TagException |
                 InvalidAudioFrameException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tempFile)
                    .ifPresent(File::delete);
        }
        return new AudioFileMetadata(header, tag);
    }

    private AlbumDto getAlbumDto(Tag tag) {
        var name = tag.getFirstField(FieldKey.ALBUM).toString();
        var releaseDate = Year.parse(tag.getFirstField(FieldKey.YEAR).toString());
        var genre = new GenreDto(tag.getFirstField(FieldKey.GENRE).toString());

        log.debug("\nTotal info about album:\nName - {}\nRelease date - {}\nGenre - {}", name, releaseDate, genre);
        return new AlbumDto(name, releaseDate, genre);
    }

    private ArtistDto getArtistDto(Tag tag) {
        var name = tag.getFirstField(FieldKey.ARTIST).toString();

        log.debug("\nTotal info about artist:\nName - {}", name);
        return new ArtistDto(name);
    }

    private SongDto getSongDto(Tag tag, AudioHeader header, ArtistDto artist, AlbumDto album) {
        var name = tag.getFirstField(FieldKey.TITLE).toString();
        var duration = header.getTrackLength();
        var trackNumberAlbum = Short.parseShort(tag.getFirst(FieldKey.TRACK));

        log.debug("\nTotal info about song:\nName - {}\nDuration - {}\nTrack number in album - {}", name, duration,
                  trackNumberAlbum);
        return new SongDto(name, duration, trackNumberAlbum, artist, album);
    }
}
