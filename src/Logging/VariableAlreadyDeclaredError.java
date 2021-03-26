package Logging;

public class VariableAlreadyDeclaredError extends ErrorLog {
    public VariableAlreadyDeclaredError(String text, int lineNum, int characterIndex, String lineText) {
        super(text, lineNum, characterIndex);
    }
}
