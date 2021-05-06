package logging.logs;

public class ResultIgnoredWarning extends WarningLog {
    public ResultIgnoredWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: Result of function call %s is ignored.", super.toString(), getText());
    }
}
