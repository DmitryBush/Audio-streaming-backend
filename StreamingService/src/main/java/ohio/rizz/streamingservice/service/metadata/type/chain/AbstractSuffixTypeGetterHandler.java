package ohio.rizz.streamingservice.service.metadata.type.chain;

import java.util.Objects;

abstract public class AbstractSuffixTypeGetterHandler {
    private final String contentType;
    protected AbstractSuffixTypeGetterHandler next;

    public AbstractSuffixTypeGetterHandler(String contentType) {
        this.contentType = contentType;
    }

    public AbstractSuffixTypeGetterHandler setNext(AbstractSuffixTypeGetterHandler nextHandler) {
        this.next = nextHandler;
        return nextHandler;
    }

    public String handle(String contentType) {
        if (this.contentType.equals(contentType)) {
            return getSuffix(contentType);
        }

        if (Objects.nonNull(next)) {
            next.handle(contentType);
        }
        throw new IllegalArgumentException("missing processed type: " + contentType);
    }

    protected abstract String getSuffix(String contentType);
}
