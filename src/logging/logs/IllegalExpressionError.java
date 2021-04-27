package logging.logs;

public class IllegalExpressionError extends ErrorLog {
    private String expectedType;

    public IllegalExpressionError(String text, int lineNum, int characterIndex, String expectedType) {
        super(text, lineNum, characterIndex);
        this.expectedType = expectedType;
    }

    @Override
    public String toString() {
        return String.format("IllegalExpressionError: %s is an invalid expression. Expected expression type to be %s.", this.getText(), expectedType) ;
    }
}
