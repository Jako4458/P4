package exceptions;

public class CompileTimeException extends RuntimeException {
    public CompileTimeException() {
    }

    public CompileTimeException(String message) {
        super(message);
    }

    public CompileTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileTimeException(Throwable cause) {
        super(cause);
    }

    public CompileTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
