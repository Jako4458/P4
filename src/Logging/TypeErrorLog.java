package Logging;

public class TypeErrorLog extends ErrorLog {
    private String actualType;
    private String expectedType;

    public TypeErrorLog(String name, int line, int characterIndex, String actualType, String expectedType) {
        super(name, line, characterIndex);
        this.actualType = actualType;
        this.expectedType = expectedType;
    }
}
