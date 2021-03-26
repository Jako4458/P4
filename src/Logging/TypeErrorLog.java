package Logging;

public class TypeErrorLog extends ErrorLog {
    private String actualType;
    private String expectedType;

    public TypeErrorLog(String errorText, int lineNum, int characterIndex, String actualType, String expectedType) {
        super(errorText, lineNum, characterIndex);
        this.actualType = actualType;
        this.expectedType = expectedType;
    }

    @Override
    public String toString() {
        return String.format("TypeError: %s has type %s. Expected %s.", this.getText(), actualType, expectedType) ;
    }
}
