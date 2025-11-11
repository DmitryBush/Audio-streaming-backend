package ohio.rizz.streamingservice.service.type.chain.internal.audio;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.Collections;

public class MpegSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public MpegSuffixTypeGetterHandler() {
        super(Collections.singletonList("audio/mpeg"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".mp3";
    }
}
