package Logging;

public class VariableAlreadyDeclaredError extends ErrorLog {
    public VariableAlreadyDeclaredError(String name, int line, int characterIndex) {
        super(name, line, characterIndex);
    }
}
