package Logging;

public class TooManyArgumentsError extends ErrorLog{
    public TooManyArgumentsError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("Too many arguments in call to '%s'", getText());
    }
}
