package Logging;

public class MissingReturnError extends ErrorLog {
    private String actualType;

    public MissingReturnError(String text, int lineNum, int characterIndex, String actualType) {
        super(text, lineNum, characterIndex);
        this.actualType = actualType;
    }

    @Override
    public String toString() {
        return String.format("MissingReturnError: %s has type %s. Expected return statement.", this.getText(), actualType);
    }
}
