package ohio.rizz.streamingservice.it.service.upload;

import com.redis.testcontainers.RedisContainer;
import ohio.rizz.streamingservice.service.UploadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class UploadServiceIT {
    @Autowired
    private UploadService service;

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.0-alpine");

    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @Container
    static RedisContainer redisContainer = new RedisContainer("redis:8.2-alpine");

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
        registry.add("storage-service.endpoint",
                () -> "http://127.0.0.1:%s".formatted(minIOContainer.getMappedPort(9000)));
    }

    @Test
    public void testFlacUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_FLAC.flac");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_FLAC.flac",
                    "Test_FLAC.flac",
                    "audio/flac",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testAacUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_AAC.m4a");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_AAC.m4a",
                    "Test_AAC.m4a",
                    "audio/aac",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testAlacUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_ALAC.m4a");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_ALAC.m4a",
                    "Test_ALAC.m4a",
                    "audio/aac",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testMp3Upload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_MP3.mp3");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_MP3.mp3",
                    "Test_MP3.mp3",
                    "audio/mpeg",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testOggUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_OGG.ogg");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_OGG.ogg",
                    "Test_OGG.ogg",
                    "audio/ogg",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testWavUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_WAV.wav");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_WAV.wav",
                    "Test_WAV.wav",
                    "audio/x-wav",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }

    @Test
    public void testWmaUpload() throws IOException {
        File file = new File("src/test/java/ohio/rizz/streamingservice/resource/Test_WMA.wma");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MultipartFile mockMultipartFile = new MockMultipartFile(
                    "Test_WMA.wma",
                    "Test_WMA.wma",
                    "audio/x-ms-wma",
                    inputStream
            );
            Assertions.assertDoesNotThrow(() -> service.uploadFile(mockMultipartFile));
        }
    }
}
