package logging.logs;

public class ConstantLoopExpressionWarning extends WarningLog {

    public ConstantLoopExpressionWarning(String text, int lineNum, int characterIndex) {
        super(text, lineNum, characterIndex);
    }

    @Override
    public String toString() {
        return String.format("%s: constant loop expression %s may lead to infinite loop.", super.toString(), getText());
    }
}
