package ohio.rizz.streamingservice.service.type.chain.internal.image;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class HeicSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public HeicSuffixTypeGetterHandler() {
        super(List.of("image/heic"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".heic";
    }
}
