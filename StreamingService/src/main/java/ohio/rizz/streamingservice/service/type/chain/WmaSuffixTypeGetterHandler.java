package ohio.rizz.streamingservice.service.type.chain;

import java.util.List;

public class WmaSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler{
    public WmaSuffixTypeGetterHandler() {
        super(List.of("audio/x-ms-wma", "audio/x-ms-wax"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".wma";
    }
}
