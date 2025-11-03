package ohio.rizz.streamingservice.service.type.chain;

import java.util.Collections;

public class M4aSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler{
    public M4aSuffixTypeGetterHandler() {
        super(Collections.singletonList("audio/x-m4a"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".m4a";
    }
}
