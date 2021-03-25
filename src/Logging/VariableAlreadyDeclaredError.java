package Logging;

public class VariableAlreadyDeclaredError extends ErrorLog {
    public VariableAlreadyDeclaredError(String name, int line) {
        super(String.format("Error at line %d: %s has already been declared", line, name));
    }
}
