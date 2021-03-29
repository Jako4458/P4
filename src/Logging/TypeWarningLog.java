package Logging;

public class TypeWarningLog extends WarningLog{
    private String actualType;
    private String expectedType;

    public TypeWarningLog(String errorText, int lineNum, int characterIndex, String actualType, String expectedType) {
        super(errorText, lineNum, characterIndex);
        this.actualType = actualType;
        this.expectedType = expectedType;
    }

    @Override
    public String toString() {
        return String.format("TypeWarning: %s has type %s. Did you mean %s?", this.getText(), actualType, expectedType) ;
    }

}
