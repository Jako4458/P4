import Logging.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class LogFactory {
    public Log createDuplicateVarLog(String name, ParserRuleContext ctx) {
        return null;//return new VariableAlreadyDeclaredError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createTypeError(String text, ParserRuleContext ctx, Type actual, Type expected) {
        return new TypeError(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl)actual.tree).symbol.getText(),
                ((TerminalNodeImpl)expected.tree).symbol.getText()
        );
    }

    public Log createMissingReturnError(String text, ParserRuleContext ctx, Type actual) {
        return new MissingReturnError(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl)actual.tree).symbol.getText()
        );
    }

    public Log createNotDeclaredLog(String name, ParserRuleContext ctx) {
        return null;//return new VariableNotDeclaredError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createVarNotArrayLog(String name, ParserRuleContext ctx) {
        return null;//return new VariableIsNotArrayError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
}