package ohio.rizz.streamingservice.service.type.chain;

public class FlacSuffixTypeGetterHandler extends AbstractSuffixTypeGetterHandler {
    public FlacSuffixTypeGetterHandler() {
        super("audio/x-flac");
    }

    @Override
    protected String getSuffix(String contentType) {
        return ".flac";
    }
}
