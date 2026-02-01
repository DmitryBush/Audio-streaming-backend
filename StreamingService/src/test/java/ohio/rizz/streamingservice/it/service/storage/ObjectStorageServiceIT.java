package ohio.rizz.streamingservice.it.service.storage;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import ohio.rizz.streamingservice.service.storage.BucketStreamingConstants;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ObjectStorageServiceIT {
    @Autowired
    private ObjectStorageService objectStorageService;

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.0-alpine");

    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("storage-service.endpoint",
                () -> "http://127.0.0.1:%s".formatted(minIOContainer.getMappedPort(9000)));
    }

    @Test
    public void testSaveFile() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();

        objectStorageService.saveFile(testFile, BucketStreamingConstants.AUDIO.getTitle(), objectLink);
        Assertions.assertDoesNotThrow(() ->
                objectStorageService.loadResource(BucketStreamingConstants.AUDIO.getTitle(),
                        UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString()));
    }

    @Test
    public void testSaveAsyncFile() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();

        CompletableFuture<Void> completableFuture = objectStorageService
                .saveFileAsync(testFile, BucketStreamingConstants.AUDIO.getTitle(), objectLink);
        Assertions.assertAll(
                completableFuture::join,
                () -> objectStorageService.loadResource(BucketStreamingConstants.AUDIO.getTitle(),
                        UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString())
        );
    }

    @Test
    public void testSaveStreamFile() throws IOException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();
        try (FileInputStream inputStream = new FileInputStream(testFile)) {
            objectStorageService.saveFile(inputStream, testFile.length(),
                    BucketStreamingConstants.AUDIO.getTitle(), objectLink);
            Assertions.assertDoesNotThrow(() ->
                    objectStorageService.loadResource(BucketStreamingConstants.AUDIO.getTitle(), objectLink));
        }
    }

    @Test
    public void testSaveAsyncStreamFile() throws IOException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();
        try (FileInputStream inputStream = new FileInputStream(testFile)) {
            CompletableFuture<Void> completableFuture = objectStorageService.saveFileAsync(inputStream, testFile.length(),
                    BucketStreamingConstants.AUDIO.getTitle(), objectLink);
            Assertions.assertAll(
                    completableFuture::join,
                    () -> objectStorageService.loadResource(BucketStreamingConstants.AUDIO.getTitle(), objectLink)
            );
        }
    }

    @Test
    public void testDeleteFile() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();

        objectStorageService.saveFile(testFile, BucketStreamingConstants.AUDIO.getTitle(), objectLink);
        Assertions.assertDoesNotThrow(() ->
                objectStorageService.deleteFile(BucketStreamingConstants.AUDIO.getTitle(), objectLink));
    }

    @Test
    public void testLoadStreamResource() throws FileNotFoundException {
        File testFile = ResourceUtils.getFile("classpath:test/resource/Test_FLAC.flac");
        String objectLink = UUID.nameUUIDFromBytes(testFile.getName().getBytes()).toString();

        objectStorageService.saveFile(testFile, BucketStreamingConstants.AUDIO.getTitle(), objectLink);
        Assertions.assertDoesNotThrow(() -> objectStorageService
                .loadStreamResource(BucketStreamingConstants.AUDIO.getTitle(), objectLink, 0L, 1024L));
    }
}
