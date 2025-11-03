package ohio.rizz.streamingservice.service.metadata;

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

import java.io.File;
import java.io.IOException;
import java.time.Year;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataParserService {

    public SongDto extractMetadataFromFile(File file) {
        AudioFileMetadata metadata = readAudioFileMetadata(file);

        var album = getAlbumDto(metadata.tag());
        var artist = getArtistDto(metadata.tag());
        return getSongDto(metadata.tag(), metadata.header(), artist, album);
    }

    private AudioFileMetadata readAudioFileMetadata(File file) {
        AudioHeader header;
        Tag tag;
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            header = audioFile.getAudioHeader();
            tag = audioFile.getTag();
        } catch (CannotReadException | IOException | ReadOnlyFileException | InvalidAudioFrameException
                 | TagException e) {
            throw new RuntimeException(e);
        }
        return new AudioFileMetadata(header, tag);
    }

    private AlbumDto getAlbumDto(Tag tag) {
        var name = tag.getFirstField(FieldKey.ALBUM).toString();
        var releaseDate = Year.parse(tag.getFirstField(FieldKey.YEAR).toString());
        var genre = new GenreDto(tag.getFirstField(FieldKey.GENRE).toString());

        var discMetadata = getDiskMetadata(tag.getFirst(FieldKey.DISC_NO));

        log.debug("\nTotal info about album:\nName - {}\nRelease date - {}\nGenre - {}\nDisc - {}",
                  name,
                  releaseDate,
                  genre,
                  discMetadata);
        return new AlbumDto(name, releaseDate, discMetadata.totalDisk(), genre);
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

        var discMetadata = getDiskMetadata(tag.getFirst(FieldKey.DISC_NO));

        log.debug("\nTotal info about song:\nName - {}\nDuration - {}\nTrack number in album - {}\nDisc - {}", name,
                  duration, trackNumberAlbum, discMetadata);
        return new SongDto(name, discMetadata.currentDisk(), duration, trackNumberAlbum, artist, album);
    }

    private DiscMetadata getDiskMetadata(String rawData) {
        var separatedStrings = rawData.split("/");
        return new DiscMetadata(Short.parseShort(separatedStrings[0]), Short.parseShort(separatedStrings[1]));
    }
}
