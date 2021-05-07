package logging.logs;

import logging.logs.ErrorLog;

public class InvalidOperatorError extends ErrorLog {
    private String type;
    private String right;
    private String left;

    public InvalidOperatorError(String text, int lineNum, int characterIndex, String left, String right) {
        super(text, lineNum, characterIndex);
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" TypeError: %s operator cannot be used for type %s with operand type %s", this.getText(), this.left, this.right);
    }
}
