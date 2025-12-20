package ohio.rizz.streamingservice.validation;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileNameValidator {
    public void validateFileName(String filename) {
        if (Objects.isNull(filename)) {
            throw new IllegalArgumentException("Filename couldn't be null");
        }
        if (filename.isBlank()) {
            throw new IllegalArgumentException("File length doesn't match maximum OS file length or blank");
        }
        if (filename.contains("..") || filename.contains("\\") || filename.contains("/")) {
            throw new IllegalArgumentException("Filename contain illegal characters");
        }
    }
}
