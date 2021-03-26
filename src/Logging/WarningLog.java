package Logging;

/**
 * A generic warning log class.
 */
public class WarningLog extends Log {
    private String text;

    public WarningLog(String text, int lineNum, int characterIndex) {
        super(LogType.WARNING, lineNum, characterIndex);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getColour() {
        return "\u001B[4;34m";
    }
}
