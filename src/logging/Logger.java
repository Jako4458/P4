package logging;

import com.sun.tools.javac.Main;
import logging.logs.ErrorLog;
import logging.logs.Log;
import logging.logs.WarningLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple singleton implementation of ILogger.
 */
public class Logger implements ILogger {
    private final ArrayList<Log> logs;
    private final int indentations = 5;
    private String[] sourceProg;
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
    public void print(boolean all) {
        for (Log log : logs) {
            if (!(log instanceof WarningLog) || all) {
                String formattedMessage = formatMessage(log);
                System.out.println(formattedMessage);
            }
        }
    }

    @Override
    public void dump(boolean all) {
        print(all);
        clear();
    }

    public List<Log> getLogs() {
        return this.logs;
    }

    private String formatMessage(Log log) {
        String logMessage = log.toString();
        String message;
        if (logMessage.equals(""))
            throw new NullPointerException("Cannot format empty message");

        message = String.format("%s%s:%s:%s\u001B[0m %s\n", log.getColour(), log.type.toString(), log.getLineNum(), log.getCharacterIndex(), logMessage);
        message += " ".repeat(indentations) + this.sourceProg[log.getLineNum() - 1] + "\n";
        message += " ".repeat(indentations + log.getCharacterIndex()) + "↑\n";

        return message;
    }

    public void setSourceProg(String[] sourceProg) {
        this.sourceProg = sourceProg;
    }

    public boolean containsErrors() {
        for (Log log : logs) {
            if (log instanceof ErrorLog)
                return true;
        }
        return false;
    }

    public boolean containsWarnings() {
        for (Log log : logs) {
            if (log instanceof WarningLog)
                return true;
        }

        return false;
    }
}
