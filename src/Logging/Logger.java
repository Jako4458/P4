package Logging;

import java.util.ArrayList;

/**
 * Simple singleton implementation of ILogger.
 */
public class Logger implements ILogger {
    private final ArrayList<Log> logs;

    public static Logger shared = new Logger();

    public Logger() {
        logs = new ArrayList<>();
    }

    @Override
    public void add(Log log) {
        if (log == null)
            throw new NullPointerException("Trying to add null to logs.");

        logs.add(log);
    }

    @Override
    public void clear() {
        logs.clear();
    }

    @Override
    public void print() {
        for (Log log : logs) {
            String formattedMessage = format_message(log.message, log.type);

            System.out.println(formattedMessage);
        }
    }

    private String format_message(String message, LogType type) {
        if (message == null)
            throw new NullPointerException("Cannot format null message.");
        if (type == null)
            throw new NullPointerException("Cannot format null type.");

        switch (type) {
            case WARNING:
                return String.format("Warning: %s", message);
            case INFO:
                return String.format("Info: %s", message);
            case ERROR:
                return String.format("Error: %s", message);
            default:
                throw new RuntimeException(String.format("Unable to format message due to unsupported log type: %s", message));
        }
    }
}
