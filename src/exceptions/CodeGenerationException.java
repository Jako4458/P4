package exceptions;

public class CodeGenerationException extends RuntimeException {

    public CodeGenerationException() {
        super();
    }

    public CodeGenerationException(String message) {
        super(message);
    }

    public CodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeGenerationException(Throwable cause) {
        super(cause);
    }

    protected CodeGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
