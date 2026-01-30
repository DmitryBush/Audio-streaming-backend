package ohio.rizz.streamingservice.unit.service.metadata;

import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.service.metadata.SongMetadataParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

public class SongMetadataParserServiceUnitTest {
    private final SongMetadataParserService songMetadataParserService = new SongMetadataParserService();

    @Test
    public void testWavParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_WAV.wav");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test_WAV.wav", parsedSongData.name()),
                () -> Assertions.assertEquals(17, parsedSongData.duration())
        );
    }

    @Test
    public void testFlacParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_FLAC.flac");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(18, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 1),
                        parsedSongData.albumDto().releaseDate())
        );
    }

    @Test
    public void testMp3Parsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_MP3.mp3");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(18, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 30),
                        parsedSongData.albumDto().releaseDate())
        );
    }

    @Test
    public void testOggParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_OGG.ogg");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(18, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 1),
                        parsedSongData.albumDto().releaseDate())
        );
    }

    @Test
    public void testWmaParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_WMA.wma");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(20, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 1),
                        parsedSongData.albumDto().releaseDate())
        );
    }

    @Test
    public void testAlacParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_ALAC.m4a");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(18, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 1),
                        parsedSongData.albumDto().releaseDate())
        );
    }

    @Test
    public void testAacParsing() {
        File testFile = new File("src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_ALAC.m4a");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test", parsedSongData.name()),
                () -> Assertions.assertEquals(18, parsedSongData.duration()),
                () -> Assertions.assertEquals("Sound flow from garage band", parsedSongData.albumDto().name()),
                () -> Assertions.assertEquals("Dmitry Bush", parsedSongData.artistDto().name()),
                () -> Assertions.assertEquals(LocalDate.of(2026, 1, 1),
                        parsedSongData.albumDto().releaseDate())
        );
    }
}
