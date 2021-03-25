package Logging;

/**
 * A generic info log class.
 */
public class InfoLog extends Log {
    public InfoLog(String message) {
        super(message, LogType.INFO);
    }
}
