package logging.logs;

public class InfiniteLoopWarning extends WarningLog {
    public InfiniteLoopWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: loop expression %s leads to infinite loop.", super.toString(), getText());
    }
}
