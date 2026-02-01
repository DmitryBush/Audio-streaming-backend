package ohio.rizz.streamingservice.unit.service.metadata;

import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.service.metadata.SongMetadataParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class SongMetadataParserServiceUnitTest {
    private final SongMetadataParserService songMetadataParserService = new SongMetadataParserService();

    @Test
    public void testWavParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_WAV.wav");

        SongDto parsedSongData = songMetadataParserService.extractMetadataFromFile(testFile);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Test_WAV.wav", parsedSongData.name()),
                () -> Assertions.assertEquals(17, parsedSongData.duration())
        );
    }

    @Test
    public void testFlacParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");

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
    public void testMp3Parsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_MP3.mp3");;

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
    public void testOggParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_OGG.ogg");

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
    public void testWmaParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_WMA.wma");

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
    public void testAlacParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_ALAC.m4a");

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
    public void testAacParsing() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_AAC.m4a");

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
