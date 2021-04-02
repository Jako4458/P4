import Logging.*;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class LogFactory {
    public Log createVariableAlreadyDeclaredLog(String name, ParserRuleContext ctx) {
        return new VariableAlreadyDeclaredError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
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

    public Log createUnassignedVariableLog(String text, ParserRuleContext ctx){
        return new UnassignedVariableWarning(text, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createVariableNotDeclaredLog(String text, ParserRuleContext ctx) {
        return new VariableNotDeclaredError(text, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createVarNotArrayLog(String name, ParserRuleContext ctx) {
        return null;//return new VariableIsNotArrayError(name, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createMCFuncWrongReturnType(String text, ParserRuleContext ctx, Type actual, Type expected) {
        return new MCFuncWrongReturnType(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl) actual.tree).symbol.getText(),
                ((TerminalNodeImpl) expected.tree).symbol.getText()
        );
    }

    public Log createTypeWarning(String text, ParserRuleContext ctx, Type actual, Type expected) {
        return new TypeWarningLog(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl)actual.tree).symbol.getText(),
                ((TerminalNodeImpl)expected.tree).symbol.getText()
        );
    }

    public Log createCannotBeVoid(String text, ParserRuleContext ctx, Type type) {
        return new CannotBeVoid(text, ctx.start.getLine(), ctx.start.getCharPositionInLine(), ((TerminalNodeImpl)type.tree).symbol.getText());
    }

    public Log createTooFewArgumentsError(String text, ParserRuleContext ctx) {
        return new TooFewArgumentsError(text, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createTooManyArgumentsError(String text, ParserRuleContext ctx) {
        return new TooManyArgumentsError(text, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createFuncDeclLocationNote(ParserRuleContext ctx){
        return new FuncDeclLocationNote("Function declaration here", ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createVarDeclLocationNote(ParserRuleContext ctx) {
        return new VarDeclLocationNote("Definition here", ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    public Log createInvalidOperatorError(String text, ParserRuleContext ctx, Type left, Type right) {
        return new InvalidOperatorError(text, ctx.start.getLine(),
                ctx.start.getCharPositionInLine(),
                ((TerminalNodeImpl) left.tree).symbol.getText(),
                ((TerminalNodeImpl) right.tree).symbol.getText()
        );
    }
}
