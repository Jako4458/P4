package Logging;

/**
 * A generic error log class.
 */
public class ErrorLog extends Log {
    private int line;
    private int characterIndex;
    private String name;

    public ErrorLog(String name, int line, int characterIndex) {
        super(LogType.ERROR);
        this.name = name;
        this.line = line;
        this.characterIndex = characterIndex;
    }

    public int getLine() {
        return this.line;
    }

    public int getCharacterIndex() {
        return this.characterIndex;
    }

    public String getName() {
        return this.name;
    }
}
