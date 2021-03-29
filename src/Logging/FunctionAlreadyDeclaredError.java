package Logging;

public class FunctionAlreadyDeclaredError extends ErrorLog {
    public FunctionAlreadyDeclaredError(String name, int line, int characterIndex) {
        super(name, line, characterIndex);
    }
}
