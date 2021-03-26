package Logging;

/**
 * An abstract class for representing a log entry.
 */
public abstract class Log {
    /**
     * The logs content type.
     */
    public final LogType type;

    /**
     * The logs content.
     */
    public final String message;

    protected Log(String message, LogType type) {
        if (message == null)
            throw new NullPointerException("Cannot assign log message null.");
        if (type == null)
            throw new NullPointerException("Cannot assign log type null.");

        this.type = type;
        this.message = message;
    }
}