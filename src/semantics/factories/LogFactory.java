import Logging.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class LogFactory {
    public Log createDuplicateVarLog(String name, ParserRuleContext ctx) {
        return new VariableAlreadyDeclaredError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createTypeError(String text, ParserRuleContext ctx, Type actual, Type expected) {
        return new TypeErrorLog(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl)actual.tree).symbol.getText(),
                ((TerminalNodeImpl)expected.tree).symbol.getText()
        );
    }

    public Log createNotDeclaredLog(String name, ParserRuleContext ctx) {
        return new VariableNotDeclaredError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createVarNotArrayLog(String name, ParserRuleContext ctx) {
        return new VariableIsNotArrayError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
}
