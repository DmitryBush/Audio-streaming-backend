package ohio.rizz.streamingservice.service.metadata.type;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.service.metadata.type.chain.AbstractSuffixTypeGetterHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ContentTypeService {
    private final AbstractSuffixTypeGetterHandler suffixTypeGetterHandler;

    public String getSuffixType(MultipartFile multipartFile) {
        return suffixTypeGetterHandler.handle(multipartFile.getContentType());
    }
}
