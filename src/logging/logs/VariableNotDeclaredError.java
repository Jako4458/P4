package logging.logs;

public class VariableNotDeclaredError extends ErrorLog {

    public VariableNotDeclaredError(String text, int line, int characterIndex) {
        super(text, line, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: '%s' undeclared", super.toString(), getText());
    }
}
