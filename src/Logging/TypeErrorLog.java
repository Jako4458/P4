package Logging;

public class TypeErrorLog extends ErrorLog {
    private String actualType;
    private String expectedType;

    public TypeErrorLog(String text, int line, int characterIndex, String actualType, String expectedType) {
        super(text, line, characterIndex);
        this.actualType = actualType;
        this.expectedType = expectedType;
    }

    @Override
    public String toString() {
        return String.format("TypeError: %s has type %s. Expected %s.", text, expectedType, actualType) ;
    }
}
