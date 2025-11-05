package ohio.rizz.streamingservice.service.filesystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.validation.FileNameValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilesystemService {
    private final FileNameValidator fileNameValidator;

    public File createTemporalFile(MultipartFile multipartFile, String suffix) {
        try {
            var filename = multipartFile.getOriginalFilename();
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
}
