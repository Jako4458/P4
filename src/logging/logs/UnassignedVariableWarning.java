package logging.logs;

import logging.logs.WarningLog;

public class UnassignedVariableWarning extends WarningLog {
    public UnassignedVariableWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: %s is declared but never assigned", super.toString(), getText());
    }
}
