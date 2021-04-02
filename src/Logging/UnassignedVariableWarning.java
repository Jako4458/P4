package Logging;

public class UnassignedVariableWarning extends WarningLog {
    public UnassignedVariableWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }
}
