package ohio.rizz.streamingservice.service.type.chain;

import java.util.List;

public class WavSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler{
    public WavSuffixTypeGetterHandler() {
        super(List.of("audio/x-wav", "audio/vnd.wave"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".wav";
    }
}
