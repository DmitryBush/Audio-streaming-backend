package ohio.rizz.streamingservice.unit.service.filesystem;

import ohio.rizz.streamingservice.service.filesystem.FileSystemService;
import ohio.rizz.streamingservice.validation.FileNameValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class FileSystemServiceUnitTest {
    @MockitoBean
    private FileNameValidator fileNameValidator;
    @Autowired
    private FileSystemService fileSystemService;

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.0-alpine");

    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @Test
    public void testTemporalFileCreatingByMultipart() {
        String testFilePath = "src/test/java/ohio/rizz/streamingservice/unit/service/metadata/Test_FLAC.flac";
        File tempFile;
        Mockito.doNothing().when(fileNameValidator).validateFileName(Mockito.anyString());

        try (InputStream inputStream = new FileInputStream(testFilePath)) {
            MultipartFile mockMultipartFile = new MockMultipartFile("Test_Flac.flac",
                    "Test_Flac.flac", "audio/flac", inputStream);
            tempFile = fileSystemService.createTemporalFile(mockMultipartFile, ".flac");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertAll(
                () -> Assertions.assertTrue(tempFile.exists()),
                () -> Assertions.assertTrue(tempFile.isFile()),
                () -> Assertions.assertTrue(tempFile.canRead()),
                () -> Assertions.assertTrue(tempFile.canWrite())
        );
        boolean resultOfDeletion = tempFile.delete();
        Assertions.assertAll(
                () -> Assertions.assertTrue(resultOfDeletion),
                () -> Assertions.assertFalse(tempFile.exists())
        );
    }

    @Test
    public void testTemporalFileCreating() {
        File tempFile;
        Mockito.doNothing().when(fileNameValidator).validateFileName(Mockito.anyString());

        tempFile = fileSystemService.createTemporalFile("Test_Flac.flac", ".flac");

        Assertions.assertAll(
                () -> Assertions.assertTrue(tempFile.exists()),
                () -> Assertions.assertTrue(tempFile.isFile()),
                () -> Assertions.assertTrue(tempFile.canRead()),
                () -> Assertions.assertTrue(tempFile.canWrite())
        );
        boolean resultOfDeletion = tempFile.delete();
        Assertions.assertAll(
                () -> Assertions.assertTrue(resultOfDeletion),
                () -> Assertions.assertFalse(tempFile.exists())
        );
    }

    @Test
    public void testByteArrayCopying() throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");

        fileSystemService.copyByteArrayToFile(tempFile, new byte[]{1, 0, 1});

        try (FileInputStream fileInputStream = new FileInputStream(tempFile)) {
            byte[] byteArray = fileInputStream.readAllBytes();

            Assertions.assertEquals(Arrays.toString(new byte[]{1,0,1}), Arrays.toString(byteArray));
        }

        if (!tempFile.delete()) {
            throw new RuntimeException("An error has occurred while deleting file");
        }
    }
}
