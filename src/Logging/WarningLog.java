package Logging;

/**
 * A generic warning log class.
 */
public class WarningLog extends Log {
    public WarningLog(String message) {
        super(message, LogType.WARNING);
    }
}
