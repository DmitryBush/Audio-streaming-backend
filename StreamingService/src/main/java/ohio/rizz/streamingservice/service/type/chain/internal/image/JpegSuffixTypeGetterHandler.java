package ohio.rizz.streamingservice.service.type.chain.internal.image;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class JpegSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public JpegSuffixTypeGetterHandler() {
        super(List.of("image/jpeg", "image/pjpeg"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".jpeg";
    }
}
