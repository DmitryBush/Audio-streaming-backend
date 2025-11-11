package ohio.rizz.streamingservice.service.type.chain.internal.audio;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class OggSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public OggSuffixTypeGetterHandler() {
        super(List.of("audio/ogg", "audio/vorbis", "audio/x-ogg"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".ogg";
    }
}
