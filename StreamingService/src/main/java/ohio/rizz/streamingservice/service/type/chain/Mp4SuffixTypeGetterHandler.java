package ohio.rizz.streamingservice.service.type.chain;

import java.util.Collections;

public class Mp4SuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler{
    public Mp4SuffixTypeGetterHandler() {
        super(Collections.singletonList("audio/mp4"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".mp4";
    }
}
