package logging.logs;

public class DivisionByZeroError extends ErrorLog {
    public DivisionByZeroError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return "CompileError: Cannot divide by 0";
    }
}
