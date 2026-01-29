package ohio.rizz.streamingservice.service.filesystem;

import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.validation.FileNameValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Service for file system operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileSystemService {
    private final FileNameValidator fileNameValidator;

    /**
     * A method for creating a temporary file by {@link MultipartFile} and suffix
     * @param multipartFile File coming from the request
     * @param suffix The string specified at the end of the file name
     * @return Temporal file
     * @implNote Temporal file will always be deleted upon successful exit from the application
     */
    public File createTemporalFile(MultipartFile multipartFile, String suffix) {
        var filename = multipartFile.getOriginalFilename();
        return getTemporalFile(filename, suffix);
    }

    /**
     * A method for creating a temporary file by filename and suffix
     * @param filename Filename specified in {@link String}
     * @param suffix The {@link String} specified at the end of the file name
     * @return Temporal file
     * @implNote Temporal file will always be deleted upon successful exit from the application
     */
    public File createTemporalFile(String filename, String suffix) {
        return getTemporalFile(filename, suffix);
    }

    @NotNull
    private File getTemporalFile(String filename, String suffix) {
        try {
            if (Objects.nonNull(filename)) {
                fileNameValidator.validateFileName(filename);

                var file = File.createTempFile(filename, suffix);
                file.deleteOnExit();
                return file;
            }
            throw new IllegalArgumentException("Filename couldn't be null");
        } catch (IllegalArgumentException e) {
            log.error("Filename didn't pass validation: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyByteArrayToFile(File file, byte[] byteArray) {
        try {
            if (!file.exists() || !file.isFile()) {
                throw new FileNotFoundException();
            }
            Files.write(byteArray, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
