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
        ctx.scope = factory.createFuncScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        Function newFunc = new Function(ctx);
        functions.put(newFunc.getName(), newFunc);

        String name = ctx.ID().getText();
        if (ctx.primaryType() != null)
            ctx.type = ctx.primaryType().type;
        else
            ctx.type = null;
        List<SimpleEntry> paramIDs = new ArrayList<>();

        for (int i = 0; i < ctx.params().param().size(); i++) {
            String paramName = ctx.params().param(i).ID().getText();
            Type paramType = ctx.params().param(i).primaryType().type;
            paramIDs.add(entryFac.createFromType(paramName, paramType));
        }

        this.currentScope.addVariable(name, entryFac.createFunctionEntry(name, ctx.type, paramIDs));

        while (!this.currentScope.isFunction()) {
            exitScope();
        }
        exitScope();
    }

    @Override
    public void exitParam(MinespeakParser.ParamContext ctx) {
        String name = ctx.ID().getText();
        ctx.type = ctx.primaryType().type;
        this.currentScope.addVariable(name, entryFac.createFromType(name, ctx.type));
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        if (ctx.retVal() != null) {
            ctx.type = ctx.retVal().type;
        } else {
            ctx.type = null;
        }
        exitScope();
    }

    @Override
    public void exitRetVal(MinespeakParser.RetValContext ctx) {
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
    public void exitDcls(MinespeakParser.DclsContext ctx) {
        List<TerminalNode> dclsIDs = ctx.ID();
        List<MinespeakParser.PrimaryTypeContext> dclsTypes = ctx.primaryType();

        for (int i = 0; i < dclsIDs.size(); i++) {
            String name = dclsIDs.get(i).getText();
            Type type = dclsTypes.get(i).type;
            this.currentScope.addVariable(name, entryFac.createFromType(name, type));
        }
    }

    @Override
    public void exitInstan(MinespeakParser.InstanContext ctx) {
        List<TerminalNode> instanIDs = ctx.ID();
        List<MinespeakParser.PrimaryTypeContext> instanTypes = ctx.primaryType();

        for (int i = 0; i < instanIDs.size(); i++) {
            String name = instanIDs.get(i).getText();
            Type type = instanTypes.get(i).type;
            this.currentScope.addVariable(name, entryFac.createFromType(name, type));
        }
    }

    @Override
    public void enterArrayAccess(MinespeakParser.ArrayAccessContext ctx) {
        SymEntry entry = this.currentScope.lookup(ctx.ID().getText());

        if (entry == null) {
            // error
        }
    }

    @Override
    public void enterRvalue(MinespeakParser.RvalueContext ctx) {
        SymEntry entry = this.currentScope.lookup(ctx.ID().getText());

        if (entry == null) {
            // error
        }
    }

    @Override
    public void exitPrimaryType(MinespeakParser.PrimaryTypeContext ctx) {
        Type type = ctx.primitiveType().type;
        if (ctx.ARRAY() != null) {
            ctx.type = new ArrayType(ctx, type);
        } else {
            ctx.type = type;
        }
    }

    @Override
    public void exitPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        if (ctx.BOOL() != null)
            ctx.type = Type._bool;
        else if (ctx.BLOCK() != null)
            ctx.type = Type._block;
        else if (ctx.NUM() != null)
            ctx.type = Type._num;
        else
            ctx.type = Type._string;
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }
}
