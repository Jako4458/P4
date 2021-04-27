package logging.logs;

public class MCFuncWrongReturnType extends TypeError {
    public MCFuncWrongReturnType(String errorText, int lineNum, int characterIndex, String actualType, String expectedType) {
        super(errorText, lineNum, characterIndex, actualType, expectedType);
    }

    @Override
    public String toString() {
        return super.toString() + " MCFunctions cannot return a value.";
    }
}
