package ohio.rizz.streamingservice.service.type.chain.internal.image;

import ohio.rizz.streamingservice.service.type.chain.AbstractSuffixTypeGetterHandler;

import java.util.List;

public class PngSuffixTypeGetter extends AbstractSuffixTypeGetterHandler {
    public PngSuffixTypeGetter() {
        super(List.of("image/png"));
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".png";
    }
}
