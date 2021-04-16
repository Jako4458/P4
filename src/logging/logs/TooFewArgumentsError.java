package logging.logs;

import logging.logs.ErrorLog;

public class TooFewArgumentsError extends ErrorLog {

    public TooFewArgumentsError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("Too few arguments in function '%s'", getText());
    }
}
