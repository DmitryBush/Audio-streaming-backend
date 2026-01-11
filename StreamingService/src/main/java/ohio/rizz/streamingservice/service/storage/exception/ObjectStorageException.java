package ohio.rizz.streamingservice.service.storage.exception;

public class ObjectStorageException extends RuntimeException {
    public ObjectStorageException() {
    }

    public ObjectStorageException(String message) {
        super(message);
    }

    public ObjectStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
