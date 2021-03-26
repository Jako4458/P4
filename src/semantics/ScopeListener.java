import Logging.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeListener extends MinespeakBaseListener {
    private Scope currentScope;
    private final Map<String, FuncEntry> functions;
    private final ScopeFactory factory = new ScopeFactory();
    private final EntryFactory entryFac = new EntryFactory();
    private boolean isInvalidFunc = false;

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
        exitScope();
    }

    @Override
    public void enterMcFunc(MinespeakParser.McFuncContext ctx) {
        entryFac.setMCFunction();
    }

    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        if (functions.containsKey(ctx.ID().getText())) {
            Logger.shared.add(new FunctionAlreadyDeclaredError(ctx.ID().getText(), ctx.start.getLine()));
            this.isInvalidFunc = true;
        } else {
            ctx.scope = factory.createFuncScope(this.currentScope);
            enterScope(ctx.scope);
        }
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        if (this.isInvalidFunc) {
            this.isInvalidFunc = false;
            this.entryFac.resetMCFunction();
        } else {
            String name = ctx.ID().getText();
            ctx.type = ctx.primaryType().type;

            List<SimpleEntry> paramIDs = new ArrayList<>();

            for (MinespeakParser.ParamContext param : ctx.params().param()) {
                String paramName = param.ID().getText();
                Type paramType = param.primaryType().type;
                paramIDs.add(entryFac.createFromType(paramName, paramType));
            }

            FuncEntry entry = entryFac.createFunctionEntry(name, ctx.type, paramIDs);
            this.addToScope(ctx, name, entry);
            this.functions.put(name, entry);

            exitScope();
        }
    }

    @Override
    public void exitParam(MinespeakParser.ParamContext ctx) {
        String name = ctx.ID().getText();
        ctx.type = ctx.primaryType().type;
        this.addToScope(ctx, name, entryFac.createFromType(name, ctx.type));
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.type = ctx.retVal() != null ? ctx.retVal().type : Type._void;
        exitScope();
    }

    @Override
    public void exitRetVal(MinespeakParser.RetValContext ctx) {
        ctx.type = ctx.expr().type;
        this.addToScope(ctx, "return", entryFac.createFromType("return", ctx.type));
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
    }

    @Override
    public void exitForeachInit(MinespeakParser.ForeachInitContext ctx) {
        String name = ctx.ID().getText();
        Type type = ctx.primaryType().type;
        this.addToScope(ctx, name, entryFac.createFromType(name, type));
        ctx.type = type;
    }

    @Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        exitScope();
    }

    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        ctx.scope = factory.createScope(this.currentScope);
        enterScope(ctx.scope);
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
        addMultpleToScope(ctx);
    }

    @Override
    public void exitInstan(MinespeakParser.InstanContext ctx) {
        addMultpleToScope(ctx);
    }

    @Override
    public void enterArrayAccess(MinespeakParser.ArrayAccessContext ctx) {
        Type tempType = this.lookupTypeInScope(ctx, ctx.ID().getText());
        if (!(tempType instanceof ArrayType))
            Logger.shared.add(new VariableIsNotArrayError(ctx.ID().getText(), ctx.start.getLine()));
        else
            ctx.type = tempType;
    }

    @Override
    public void enterRvalue(MinespeakParser.RvalueContext ctx) {
        ctx.type = this.lookupTypeInScope(ctx, ctx.ID().getText());
    }

    @Override
    public void exitPrimaryType(MinespeakParser.PrimaryTypeContext ctx) {
        Type type = ctx.primitiveType().type;
        if (ctx.lArray() != null || ctx.ARRAY() != null) {
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
        else if (ctx.STRING() != null)
            ctx.type = Type._string;
        else if (ctx.VECTOR2() != null)
            ctx.type = Type._vector2;
        else if (ctx.VECTOR3() != null)
            ctx.type = Type._vector3;
        else
            ctx.type = Type._error;
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    private void addToScope(ParserRuleContext ctx, String key, SymEntry var) {
        if (!this.currentScope.addVariable(key, var)) {
            Logger.shared.add(new VariableAlreadyDeclaredError(key, ctx.start.getLine()));
        }
    }

    private Type lookupTypeInScope(ParserRuleContext ctx, String key) {
        SymEntry entry = this.currentScope.lookup(key);

        if (entry == null)
            Logger.shared.add(new VariableNotDeclaredError(key, ctx.start.getLine()));

        return entry == null ? null : entry.getType();
    }

    private void addMultpleToScope(ParserRuleContext ctx) {
        List<TerminalNode> ids;
        List<MinespeakParser.PrimaryTypeContext> types;

        if (ctx instanceof MinespeakParser.DclsContext) {
            ids = ((MinespeakParser.DclsContext)ctx).ID();
            types = ((MinespeakParser.DclsContext)ctx).primaryType();
        } else if (ctx instanceof MinespeakParser.InstanContext) {
            ids = ((MinespeakParser.InstanContext)ctx).ID();
            types = ((MinespeakParser.InstanContext)ctx).primaryType();
        } else {
            return;
        }

        for (int i = 0; i < ids.size(); i++) {
            String name = ids.get(i).getText();
            Type type = types.get(i).type;
            this.addToScope(ctx, name, entryFac.createFromType(name, type));
        }
    }
}
