package logging.logs;

import logging.ANSI_COLORS;
import logging.LogType;
import logging.logs.Log;

/**
 * A generic info log class.
 */
public class InfoLog extends Log {
    private String message;

    public InfoLog(String message, int lineNum, int characterIndex) {
        super(LogType.INFO, lineNum, characterIndex);
        this.message = message;
    }

    @Override
    public String getText() {
        return this.message;
    }

    @Override
    public String getColour() {
        return ANSI_COLORS.ANSI_YELLOW_UNDERLINE;
    }
}
