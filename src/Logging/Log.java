package Logging;

/**
 * An abstract class for representing a log entry.
 */
public abstract class Log {
    /**
     * The logs content type.
     */
    public final LogType type;

    protected Log(LogType type) {
        if (type == null)
            throw new NullPointerException("Cannot assign log type null.");

        this.type = type;
    }
}
