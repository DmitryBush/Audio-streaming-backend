package ohio.rizz.streamingservice.service.type.chain;

import java.util.List;
import java.util.Objects;

abstract public class AbstractSuffixTypeGetterHandler implements SuffixTypeGetterHandler {
    private final List<String> contentTypes;
    protected SuffixTypeGetterHandler next;

    public AbstractSuffixTypeGetterHandler(List<String> contentTypes) {
        this.contentTypes = contentTypes;
    }

    @Override
    public SuffixTypeGetterHandler setNext(SuffixTypeGetterHandler nextHandler) {
        this.next = nextHandler;
        return nextHandler;
    }

    public String handle(String contentType) {
        for (var type : contentTypes) {
            if (type.equals(contentType)) {
                return getSuffix(contentType);
            }
        }

        if (Objects.nonNull(next)) {
            return next.handle(contentType);
        }
        throw new IllegalArgumentException("missing processed type: " + contentType);
    }

    protected abstract String getSuffix(String contentType);
}
