import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeListener extends MinespeakBaseListener {
    private Scope currentScope;
    private Map<String, Function> functions;
    private final ScopeFactory factory = new ScopeFactory();
    private boolean nextFuncIsMCFunc;

    public ScopeListener() {
        this.currentScope = null;
        this.functions = new HashMap<>();
        this.nextFuncIsMCFunc = false;
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {
        this.currentScope = ctx.scope.getParent();
    }

    @Override
    public void enterBlock(MinespeakParser.BlockContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitBlock(MinespeakParser.BlockContext ctx) {
        if (!ctx.scope.isFunction() && ctx.scope.getParent() != null)
            this.currentScope = ctx.scope;
        else
            throw new RuntimeException();
    }

    @Override
    public void enterMcFunc(MinespeakParser.McFuncContext ctx) {
        this.nextFuncIsMCFunc = true;
    }

    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        Function newFunc = new Function(ctx);
        functions.put(newFunc.getName(), newFunc);
        this.currentScope.addVariable(newFunc.getName(), new FuncEntry(this.nextFuncIsMCFunc, ctx));
        this.nextFuncIsMCFunc = false;
        ctx.scope = factory.createFuncScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        while (!this.currentScope.isFunction()) {
            this.currentScope = this.currentScope.getParent();
        }
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterParam(MinespeakParser.ParamContext ctx) {
        this.currentScope.addVariable(ctx.ID().getText(), new SimpleEntry(ctx));
    }

    @Override
    public void exitParam(MinespeakParser.ParamContext ctx) {
        super.exitParam(ctx);
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterRetVal(MinespeakParser.RetValContext ctx) {
        this.currentScope.addVariable("return", new SimpleEntry(ctx));
    }

    @Override
    public void enterDoWhile(MinespeakParser.DoWhileContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterForeach(MinespeakParser.ForeachContext ctx) {
        super.enterForeach(ctx);
    }

    @Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        super.exitForeach(ctx);
    }

    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        super.enterForStmnt(ctx);
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        super.exitForStmnt(ctx);
    }

    @Override
    public void enterIfStmnt(MinespeakParser.IfStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterIfBody(MinespeakParser.IfBodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        this.currentScope = ctx.scope;
    }

    @Override
    public void exitIfBody(MinespeakParser.IfBodyContext ctx) {
        this.currentScope = this.currentScope.getParent();
    }

    @Override
    public void enterDcls(MinespeakParser.DclsContext ctx) {
        List<TerminalNode> dclsIDs = ctx.ID();
        List<MinespeakParser.PrimTypeContext> dclsTypes = ctx.primType();

        for (int i = 0; i < dclsIDs.size(); i++) {
            this.currentScope.addVariable(dclsIDs.get(i).getText(), new SimpleEntry(dclsTypes.get(i)));
        }
    }

    @Override
    public void exitDcls(MinespeakParser.DclsContext ctx) {
        super.exitDcls(ctx);
    }

    @Override
    public void enterInstan(MinespeakParser.InstanContext ctx) {
        super.enterInstan(ctx);
    }

    @Override
    public void exitInstan(MinespeakParser.InstanContext ctx) {
        super.exitInstan(ctx);
    }

    @Override
    public void enterPrimType(MinespeakParser.PrimTypeContext ctx) {
        super.enterPrimType(ctx);
    }

    @Override
    public void exitPrimType(MinespeakParser.PrimTypeContext ctx) {
        super.exitPrimType(ctx);
    }

    @Override
    public void enterPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        super.enterPrimitiveType(ctx);
    }

    @Override
    public void exitPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        super.exitPrimitiveType(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
