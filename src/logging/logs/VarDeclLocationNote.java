package logging.logs;

import logging.logs.InfoLog;

public class VarDeclLocationNote extends InfoLog {
    public VarDeclLocationNote(String message, int lineNum, int characterIndex) {
        super(message, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return getText();
    }
}
