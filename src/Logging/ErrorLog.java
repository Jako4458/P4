package Logging;

/**
 * A generic error log class.
 */
public class ErrorLog extends Log {
    public ErrorLog(String message) {
        super(message, LogType.ERROR);
    }
}
