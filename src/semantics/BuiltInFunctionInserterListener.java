import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class BuiltInFunctionInserterListener extends MinespeakBaseListener {
    public TokenStreamRewriter rewriter;
    private BuiltInFunctions builtInFunctions = new BuiltInFunctions();

    public BuiltInFunctionInserterListener(CommonTokenStream tokens) {
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        for (String function : builtInFunctions.getFunctions()) {
            rewriter.insertBefore(ctx.stop, function);
        }
    }
}
