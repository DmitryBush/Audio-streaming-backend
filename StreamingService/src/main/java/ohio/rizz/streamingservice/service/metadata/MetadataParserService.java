package ohio.rizz.streamingservice.service.metadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.GenreDto;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.service.metadata.exception.AbsentImportantMetadataException;
import ohio.rizz.streamingservice.validation.FileNameValidator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataParserService {
    private final Pattern metadataPattern = Pattern.compile("(?<=\")[^\"]+?(?=\")");

    public SongDto extractMetadataFromFile(File file) {
        AudioFileMetadata metadata = readAudioFileMetadata(file);

        try {
            var album = getAlbumDto(metadata.tag());
            var artist = getArtistDto(metadata.tag());
            return getSongDto(metadata.tag(), metadata.header(), artist, album);
        } catch (AbsentImportantMetadataException e) {
            return new SongDto(file.getName(), null, metadata.header().getTrackLength(),
                               null, null, null);
        }
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
        var name = Optional.ofNullable(tag.getFirstField(FieldKey.ALBUM))
                .map(TagField::toString)
                .map(MetadataParserService::removeZeroBit)
                .map(this::extractMetadataText)
                .orElse(null);
        if (Objects.isNull(name)) {
            return null;
        }

        var releaseDate = Optional.ofNullable(tag.getFirstField(FieldKey.YEAR))
                .map(TagField::toString)
                .map(MetadataParserService::removeZeroBit)
                .map(this::extractMetadataText)
                .map(year -> String.format("%s-01-01", year))
                .map(LocalDate::parse)
                .orElse(null);
        var genre = Optional.ofNullable(tag.getFirstField(FieldKey.GENRE))
                .map(TagField::toString)
                .map(s -> s.isBlank() ? null : s)
                .map(MetadataParserService::removeZeroBit)
                .map(this::extractMetadataText)
                .map(GenreDto::new)
                .orElse(null);

        var discMetadata = Optional.ofNullable(tag.getFirst(FieldKey.DISC_NO))
                .map(this::getDiskMetadata)
                .orElseGet(() -> new DiscMetadata((short) 1, (short) 1));

        log.debug("\nTotal info about album:\nName - {}\nRelease date - {}\nGenre - {}\nDisc - {}",
                  name,
                  releaseDate,
                  genre,
                  discMetadata);
        return new AlbumDto(name, releaseDate, discMetadata.totalDisk(), genre);
    }

    private ArtistDto getArtistDto(Tag tag) {
        var name = Optional.ofNullable(tag.getFirstField(FieldKey.ARTIST))
                .map(TagField::toString)
                .map(MetadataParserService::removeZeroBit)
                .map(this::extractMetadataText)
                .orElse(null);
        if (Objects.isNull(name)) {
            return null;
        }

        log.debug("\nTotal info about artist:\nName - {}", name);
        return new ArtistDto(name);
    }

    private SongDto getSongDto(Tag tag, AudioHeader header, ArtistDto artist, AlbumDto album) {
        var name = Optional.ofNullable(tag.getFirstField(FieldKey.TITLE))
                .map(TagField::toString)
                .map(MetadataParserService::removeZeroBit)
                .map(this::extractMetadataText)
                .orElseThrow(() -> new AbsentImportantMetadataException("The track title must be specified"));
        var duration = header.getTrackLength();
        var trackNumberAlbum = Optional.ofNullable(tag.getFirst(FieldKey.TRACK))
                .map(s -> s.isBlank() ? null : s)
                .map(Short::parseShort)
                .orElse(null);

        var discMetadata = Optional.ofNullable(tag.getFirst(FieldKey.DISC_NO))
                .map(this::getDiskMetadata)
                .orElseGet(() -> new DiscMetadata((short) 1, (short) 1));

        log.debug("\nTotal info about song:\nName - {}\nDuration - {}\nTrack number in album - {}\nDisc - {}", name,
                  duration, trackNumberAlbum, discMetadata);
        return new SongDto(name, discMetadata.currentDisk(), duration, trackNumberAlbum, artist, album);
    }

    private static String removeZeroBit(String s) {
        return s.replaceAll("\\x00", "");
    }

    private String extractMetadataText(String s) {
        if (s.startsWith("Text=")) {
            var textMatcher = metadataPattern.matcher(s);
            if (textMatcher.find()) {
                return s.substring(textMatcher.start(), textMatcher.end());
            }
            throw new AbsentImportantMetadataException("The track title must be specified");
        }
        return s;
    }

    private DiscMetadata getDiskMetadata(String rawData) {
        if (rawData.isBlank()) {
            return null;
        } else if (rawData.contains("/") || rawData.contains(":")) {
            var separatedStrings = rawData.split("[/:]");
            return new DiscMetadata(Short.parseShort(separatedStrings[0]), Short.parseShort(separatedStrings[1]));
        } else {
            return new DiscMetadata(Short.parseShort(rawData), Short.parseShort(rawData));
        }
    }
}
