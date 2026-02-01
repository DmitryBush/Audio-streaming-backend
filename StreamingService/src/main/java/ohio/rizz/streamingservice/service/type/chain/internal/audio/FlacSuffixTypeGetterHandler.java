package ohio.rizz.streamingservice.service.type.chain.internal.audio;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.Collections;
import java.util.List;

public class FlacSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public FlacSuffixTypeGetterHandler() {
        super(List.of("audio/x-flac", "audio/flac"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".flac";
    }
}
