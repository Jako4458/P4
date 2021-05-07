package logging.logs;

import logging.logs.InfoLog;

public class FuncDeclLocationNote extends InfoLog {
    public FuncDeclLocationNote(String message, int lineNum, int characterIndex) {
        super(message, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
