package ohio.rizz.streamingservice.service.type.chain.internal.image;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class HeifSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public HeifSuffixTypeGetterHandler() {
        super(List.of("image/heif"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".heif";
    }
}
