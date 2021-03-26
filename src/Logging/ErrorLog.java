package Logging;

/**
 * A generic error log class.
 */
public class ErrorLog extends Log {
    private int line;
    private int characterIndex;
    protected String text;

    public ErrorLog(String text, int line, int characterIndex) {
        super(LogType.ERROR);
        this.text = text;
        this.line = line;
        this.characterIndex = characterIndex;
    }

    public int getLine() {
        return this.line;
    }

    public int getCharacterIndex() {
        return this.characterIndex;
    }

    public String getText() {
        return this.text;
    }
}
