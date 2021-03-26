package Logging;

public class VariableIsNotArrayError extends ErrorLog{
    public VariableIsNotArrayError(String name, int line) {
        super(String.format("Error at line %d: variable %s is not an array", line, name));
    }
}
