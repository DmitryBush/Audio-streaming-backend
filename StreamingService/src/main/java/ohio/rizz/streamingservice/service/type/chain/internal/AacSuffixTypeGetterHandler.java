package ohio.rizz.streamingservice.service.type.chain.internal;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class AacSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public AacSuffixTypeGetterHandler() {
        super(List.of("audio/x-aac", "audio/aac"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".aac";
    }
}
