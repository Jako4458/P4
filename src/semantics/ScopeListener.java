import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeListener extends MinespeakBaseListener {
    private Scope currentScope;
    private final Map<String, Function> functions;
    private final ScopeFactory factory = new ScopeFactory();
    private final EntryFactory entryFac = new EntryFactory();

    public ScopeListener() {
        enterScope(null);
        this.functions = new HashMap<>();
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {
        exitScope();
    }

    @Override
    public void enterBlock(MinespeakParser.BlockContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitBlock(MinespeakParser.BlockContext ctx) {
        if (!ctx.scope.isFunction() && ctx.scope.getParent() != null)
            enterScope(ctx.scope);
        else
            throw new RuntimeException();
    }

    @Override
    public void enterMcFunc(MinespeakParser.McFuncContext ctx) {
        entryFac.setMCFunction();
    }

    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        Function newFunc = new Function(ctx);
        functions.put(newFunc.getName(), newFunc);

        String name = ctx.ID().getText();
        ctx.type = ctx.primaryType().type;
        List<SimpleEntry> paramIDs = new ArrayList<>();

        for (int i = 0; i < ctx.params().param().size(); i++) {
            String paramName = ctx.params().param(i).ID().getText();
            Type paramType = ctx.params().param(i).primaryType().type;
            paramIDs.add(entryFac.createFromType(paramName, paramType));
        }

        this.currentScope.addVariable(name, entryFac.createFunctionEntry(name, ctx.type, paramIDs));

        ctx.scope = factory.createFuncScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        while (!this.currentScope.isFunction()) {
            exitScope();
        }
        exitScope();
    }

    @Override
    public void enterParam(MinespeakParser.ParamContext ctx) {
        String name = ctx.ID().getText();
        Type type = ctx.primaryType().type;
        this.currentScope.addVariable(name, entryFac.createFromType(name, type));
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterRetVal(MinespeakParser.RetValContext ctx) {
        ctx.type = ctx.expr().type;
        this.currentScope.addVariable("return", entryFac.createFromType("return", ctx.type));
    }

    @Override
    public void enterDoWhile(MinespeakParser.DoWhileContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        exitScope();
    }

    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterForeach(MinespeakParser.ForeachContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);

        String name = ctx.ID().getText();
        Type type = ctx.primaryType().type;
        ctx.scope.addVariable(name, entryFac.createFromType(name, type));
    }

    @Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        exitScope();
    }

    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
        List<MinespeakParser.AssignContext> assigns = ctx.assign();

        for (MinespeakParser.AssignContext assign : assigns) {
            String name = assign.ID().getText();
            ctx.scope.addVariable(name, entryFac.createForAssignEntry(name));
        }
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterIfStmnt(MinespeakParser.IfStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterBody(MinespeakParser.BodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitBody(MinespeakParser.BodyContext ctx) {
        exitScope();
    }

    @Override
    public void enterDcls(MinespeakParser.DclsContext ctx) {
        List<TerminalNode> dclsIDs = ctx.ID();
        List<MinespeakParser.PrimaryTypeContext> dclsTypes = ctx.primaryType();

        for (int i = 0; i < dclsIDs.size(); i++) {
            String name = dclsIDs.get(i).getText();
            Type type = dclsTypes.get(i).type;
            this.currentScope.addVariable(name, entryFac.createFromType(name, type));
        }
    }

    @Override
    public void enterInstan(MinespeakParser.InstanContext ctx) {
        List<TerminalNode> instanIDs = ctx.ID();
        List<MinespeakParser.PrimaryTypeContext> instanTypes = ctx.primaryType();

        for (int i = 0; i < instanIDs.size(); i++) {
            String name = instanIDs.get(i).getText();
            Type type = instanTypes.get(i).type;
            this.currentScope.addVariable(name, entryFac.createFromType(name, type));
        }
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }
}
