package logging.logs;

public class NotImplementedError extends ErrorLog {

    public NotImplementedError(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", super.toString(), getText());
    }
}
