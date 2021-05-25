package exceptions;

public class BuiltInFunctionInsertionException extends RuntimeException {

    public BuiltInFunctionInsertionException() {
        super();
    }

    public BuiltInFunctionInsertionException(String message) {
        super(message);
    }

    public BuiltInFunctionInsertionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuiltInFunctionInsertionException(Throwable cause) {
        super(cause);
    }

    protected BuiltInFunctionInsertionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
