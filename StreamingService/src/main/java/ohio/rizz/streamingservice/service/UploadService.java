package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final MetadataParserService metadataParserService;

    @SneakyThrows
    public void uploadFile(MultipartFile file) {
        metadataParserService.extractMetadataFromFile(file);
    }
}
