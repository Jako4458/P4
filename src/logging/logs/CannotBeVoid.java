package logging.logs;

import logging.logs.ErrorLog;

public class CannotBeVoid extends ErrorLog {
    private String type;

    public CannotBeVoid(String text, int lineNum, int characterIndex, String type) {
        super(text, lineNum, characterIndex);
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("TypeError: %s cannot be %s", this.getText(), type);
    }
}
