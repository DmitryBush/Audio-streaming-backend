package ohio.rizz.streamingservice.unit.validation;

import ohio.rizz.streamingservice.validation.FileNameValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileNameValidatorTest {
    private final FileNameValidator fileNameValidator = new FileNameValidator();

    @Test
    public void testNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> fileNameValidator.validateFileName(null));
    }

    @Test
    public void testBlankName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> fileNameValidator.validateFileName(""));
    }

    @Test
    public void testMaximumLengthName() {
        StringBuilder filenameStringBuilder = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            filenameStringBuilder.append(i);
        }
        filenameStringBuilder.append(".flac");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileNameValidator.validateFileName(filenameStringBuilder.toString()));
    }

    @Test
    public void testEscapeCharacters() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> fileNameValidator.validateFileName("test...flac")),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> fileNameValidator.validateFileName("test\\.flac")),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> fileNameValidator.validateFileName("test/pwd.flac"))
        );
    }
}
