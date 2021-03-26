package Logging;

public class InvalidTypeError extends ErrorLog {
    public InvalidTypeError(String name, int line) {
        super(String.format("Error at line %d: type %s is invalid", line, name));
    }
}
