package logging.logs;

public class UnreachableCodeWarning extends WarningLog {

    public UnreachableCodeWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: expression %s leads to unreachable body.", super.toString(), getText());
    }
}
