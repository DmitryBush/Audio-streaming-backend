package ohio.rizz.streamingservice.service.type;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;
import ohio.rizz.streamingservice.service.type.chain.SuffixTypeGetterHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class ContentTypeService {
    private final SuffixTypeGetterHandler suffixTypeGetterHandler;

    public String getSuffixType(MultipartFile multipartFile) {
        return suffixTypeGetterHandler.handle(multipartFile.getContentType());
    }

    public String getSuffixType(String contentType) {
        return suffixTypeGetterHandler.handle(contentType);
    }

    public String getSuffix(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("The argument isn't file");
        }
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex <= 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex, filename.length() - 1);
    }
}
