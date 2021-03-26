package Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple singleton implementation of ILogger.
 */
public class Logger implements ILogger {
    private final ArrayList<Log> logs;

    public static Logger shared = new Logger();

    private Logger() {
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
            String formattedMessage = format_message(log.type);



            //System.out.println(log.toString());
        }
    }

    public List<Log> getLogs() {
        return this.logs;
    }

    private String format_message(LogType type) {
        if (type == null)
            throw new NullPointerException("Cannot format null type.");

        switch (type) {
            case WARNING:
                return String.format("Warning: %s");
            case INFO:
                return String.format("Info: %s");
            case ERROR:
                return String.format("Error: %s");
            default:
                throw new RuntimeException(String.format("Unable to format message due to unsupported log type: %s"));
        }
    }
}
