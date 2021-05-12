package logging.logs;

public class MCFuncParamsWarning extends WarningLog {
    public MCFuncParamsWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: MCFunc %s declared with formal parameters", super.toString(), getText());
    }
}
