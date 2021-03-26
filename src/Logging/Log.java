package Logging;

/**
 * An abstract class for representing a log entry.
 */
public abstract class Log {
    /**
     * The logs content type.
     */
    public final LogType type;
    private int lineNum;
    private int characterIndex;

    protected Log(LogType type, int lineNum, int characterIndex) {
        if (type == null)
            throw new NullPointerException("Cannot assign log type null.");

        this.lineNum = lineNum;
        this.characterIndex = characterIndex;
        this.type = type;
    }

    public int getLineNum(){
        return lineNum;
    }

    public int getCharacterIndex(){
        return characterIndex;
    }

    public abstract String getText();

    public abstract String getColour();
}
