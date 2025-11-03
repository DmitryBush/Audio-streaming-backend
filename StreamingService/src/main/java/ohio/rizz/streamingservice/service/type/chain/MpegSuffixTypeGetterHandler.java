package ohio.rizz.streamingservice.service.type.chain;

import java.util.Collections;

public class MpegSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler{
    public MpegSuffixTypeGetterHandler() {
        super(Collections.singletonList("audio/mpeg"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".mp3";
    }
}
