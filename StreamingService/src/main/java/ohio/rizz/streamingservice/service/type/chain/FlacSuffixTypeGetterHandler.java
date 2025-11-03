package ohio.rizz.streamingservice.service.type.chain;

import java.util.Collections;

public class FlacSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public FlacSuffixTypeGetterHandler() {
        super(Collections.singletonList("audio/x-flac"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".flac";
    }
}
