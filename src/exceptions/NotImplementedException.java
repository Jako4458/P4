package exceptions;

import org.antlr.v4.runtime.ParserRuleContext;

public class NotImplementedException extends RuntimeException {
    public ParserRuleContext ctx;

    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, ParserRuleContext ctx) {
        super(message);
        this.ctx = ctx;
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    protected NotImplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
