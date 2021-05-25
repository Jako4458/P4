package exceptions;

public class SemanticAnalysisException extends RuntimeException {

    public SemanticAnalysisException() {
        super();
    }

    public SemanticAnalysisException(String message) {
        super(message);
    }

    public SemanticAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticAnalysisException(Throwable cause) {
        super(cause);
    }

    protected SemanticAnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
