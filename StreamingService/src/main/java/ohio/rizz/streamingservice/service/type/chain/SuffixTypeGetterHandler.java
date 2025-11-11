package ohio.rizz.streamingservice.service.type.chain;

public interface SuffixTypeGetterHandler {
    String handle(String contentType);
    SuffixTypeGetterHandler setNext(SuffixTypeGetterHandler nextHandler);
}
