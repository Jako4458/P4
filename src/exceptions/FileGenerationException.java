package exceptions;

public class FileGenerationException extends RuntimeException {

    public FileGenerationException() {
        super();
    }

    public FileGenerationException(String message) {
        super(message);
    }

    public FileGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileGenerationException(Throwable cause) {
        super(cause);
    }

    protected FileGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
