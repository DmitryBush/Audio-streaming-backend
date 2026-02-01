package ohio.rizz.streamingservice.unit.service.type;

import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ContentTypeUnitTest {
    @Autowired
    private ContentTypeService contentTypeService;

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.0-alpine");

    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @Test
    public void testFlacMultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_FLAC.flac",
                    "Test_FLAC.flac",
                    "audio/flac",
                    inputStream
            );
            Assertions.assertEquals(".flac", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testMp3MultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_Mp3.mp3"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_MP3.mp3",
                    "Test_MP3.mp3",
                    "audio/mpeg",
                    inputStream
            );
            Assertions.assertEquals(".mp3", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testAacMultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_AAC.m4a"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_AAC.m4a",
                    "Test_AAC.m4a",
                    "audio/x-m4a",
                    inputStream
            );
            Assertions.assertEquals(".m4a", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testOggMultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_OGG.ogg"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_OGG.ogg",
                    "Test_OGG.ogg",
                    "audio/ogg",
                    inputStream
            );
            Assertions.assertEquals(".ogg", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testWavMultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_WAV.wav"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_WAV.wav",
                    "Test_WAV.wav",
                    "audio/x-wav",
                    inputStream
            );
            Assertions.assertEquals(".wav", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testWmaMultipartGetSuffix() throws IOException {
        try (FileInputStream inputStream =
                     new FileInputStream(ResourceUtils.getFile("classpath:test/resource/Test_WMA.wma"))) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_WMA.wma",
                    "Test_WMA.wma",
                    "audio/x-ms-wma",
                    inputStream
            );
            Assertions.assertEquals(".wma", contentTypeService.getSuffixType(mockMultipartFile));
        }
    }

    @Test
    public void testGetFlacSuffixByString() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(".flac", contentTypeService.getSuffixType("audio/flac")),
                () -> Assertions.assertEquals(".flac", contentTypeService.getSuffixType("audio/x-flac"))
        );
    }

    @Test
    public void testGetMp3SuffixByString() {
        Assertions.assertEquals(".mp3", contentTypeService.getSuffixType("audio/mpeg"));
    }

    @Test
    public void testGetAacSuffixByString() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(".m4a", contentTypeService.getSuffixType("audio/mp4")),
                () -> Assertions.assertEquals(".m4a", contentTypeService.getSuffixType("audio/aac")),
                () -> Assertions.assertEquals(".m4a", contentTypeService.getSuffixType("audio/x-m4a"))
        );
    }

    @Test
    public void testGetWavSuffixByString() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(".wav", contentTypeService.getSuffixType("audio/vnd.wave")),
                () -> Assertions.assertEquals(".wav", contentTypeService.getSuffixType("audio/x-wav"))
        );
    }

    @Test
    public void testGetWmaSuffixByString() {
        Assertions.assertEquals(".wma", contentTypeService.getSuffixType("audio/x-ms-wma"));
    }

    @Test
    public void testGetOggSuffixByString() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(".ogg", contentTypeService.getSuffixType("audio/ogg")),
                () -> Assertions.assertEquals(".ogg", contentTypeService.getSuffixType("audio/vorbis")),
                () -> Assertions.assertEquals(".ogg", contentTypeService.getSuffixType("audio/x-ogg"))
        );
    }

    @Test
    public void testGetSuffixByFile() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        Assertions.assertEquals(".flac", contentTypeService.getSuffix(file));
    }
}
