package logging.logs;

import logging.logs.ErrorLog;

public class VariableAlreadyDeclaredError extends ErrorLog {
    public VariableAlreadyDeclaredError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("Error: %s already declared.", getText());
    }
}
