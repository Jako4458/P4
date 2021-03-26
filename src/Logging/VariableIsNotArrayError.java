package Logging;

public class VariableIsNotArrayError extends ErrorLog{
    public VariableIsNotArrayError(String name, int line, int characterIndex) {
        super(name, line, characterIndex);
    }
}
