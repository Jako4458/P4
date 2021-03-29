package Logging;

/**
 * A generic error log class.
 */
public class ErrorLog extends Log {
    private String text;

    public ErrorLog(String text, int lineNum, int characterIndex) {
        super(LogType.ERROR, lineNum, characterIndex);
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String getColour() {
        // underline RED
        return ANSI_COLORS.ANSI_RED_UNDERLINE;
    }
}
