package Logging;

public class FunctionAlreadyDeclaredError extends ErrorLog {
    public FunctionAlreadyDeclaredError(String name, int line) {
        super(String.format("Error at line %d: function %s has already been declared", line, name));
    }
}
