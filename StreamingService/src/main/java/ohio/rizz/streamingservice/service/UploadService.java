package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.service.metadata.MetadataParserService;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final MetadataParserService metadataParserService;
    private final ContentTypeService contentTypeService;

    @SneakyThrows
    public void uploadFile(MultipartFile multipartFile) {
        final SongDto song;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("upload_audio_", contentTypeService.getSuffixType(multipartFile));
            multipartFile.transferTo(tempFile);
            song = metadataParserService.extractMetadataFromFile(tempFile);
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tempFile).ifPresent(file -> {
                if (!file.delete()) {
                    throw new RuntimeException("The temporary file was not deleted due to an unknown error");
                }
            });
        }
    }
}
