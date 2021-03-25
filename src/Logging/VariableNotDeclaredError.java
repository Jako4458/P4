package Logging;

public class VariableNotDeclaredError extends ErrorLog{

    public VariableNotDeclaredError(String name, int line) {
        super(String.format("Error at line %d: variable %s has not been declared", line, name));
    }
}
