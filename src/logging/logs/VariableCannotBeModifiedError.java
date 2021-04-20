package logging.logs;

public class VariableCannotBeModifiedError extends ErrorLog {
    public VariableCannotBeModifiedError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("Error: %s is a constant and cannot be assigned.", getText());
    }
}
