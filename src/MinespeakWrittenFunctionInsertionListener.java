import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.stringtemplate.v4.ST;

public class MinespeakWrittenFunctionInsertionListener extends MinespeakBaseListener {
    public TokenStreamRewriter rewriter;
    private MinespeakWrittenBuiltinFunctions builtInFunctions = new MinespeakWrittenBuiltinFunctions();

    public MinespeakWrittenFunctionInsertionListener(CommonTokenStream tokens) {
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        for (ST function : builtInFunctions.getFunctions()) {
            rewriter.insertBefore(ctx.stop, function.render());
        }
    }
}
