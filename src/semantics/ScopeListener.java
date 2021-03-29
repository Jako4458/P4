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
    private final ScopeFactory scopeFac = new ScopeFactory();
    private final EntryFactory entryFac = new EntryFactory();
    private final LogFactory logFac = new LogFactory();
    private boolean isInvalidFunc = false;

    public ScopeListener() {
        enterScope(null);
        this.functions = new HashMap<>();
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {
        exitScope();
    }

    @Override
    public void enterBlock(MinespeakParser.BlockContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
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
    public void exitMcFunc(MinespeakParser.McFuncContext ctx) {
        if (ctx.func().type != Type._void) {
            Logger.shared.add(logFac.createMCFuncWrongReturnType(ctx.func().ID().getText(), ctx, ctx.func().type, Type._void));
        }
    }

    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        if (functions.containsKey(ctx.ID().getText())) {
            Logger.shared.add(logFac.createDuplicateVarLog(ctx.ID().getText(), ctx));
            this.isInvalidFunc = true;
        } else {
            ctx.scope = scopeFac.createFuncScope(this.currentScope);
            enterScope(ctx.scope);
        }
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        String name = ctx.ID().getText();
        if (ctx.primaryType() != null)
            ctx.type = ctx.primaryType().type;
        else
            ctx.type = Type._void;

        if (this.isInvalidFunc || (ctx.type != ctx.funcBody().type)) {
            this.isInvalidFunc = false;
            this.entryFac.resetMCFunction();
            if (ctx.type != Type._void && ctx.funcBody().type == Type._void) {
                Logger.shared.add(logFac.createTypeError(ctx.ID().getText(), ctx, ctx.type, Type._void));
            } else if (ctx.type != ctx.funcBody().type) {
                Logger.shared.add(logFac.createTypeError(ctx.funcBody().retVal().expr().getText(), ctx.funcBody().retVal(), ctx.type, ctx.funcBody().retVal().type));
            } else {
                Logger.shared.add(logFac.createDuplicateFunc(ctx.ID().getText(), ctx));
            }
        } else {
            List<SimpleEntry> paramIDs = new ArrayList<>();

            for (MinespeakParser.ParamContext param : ctx.params().param()) {
                String paramName = param.ID().getText();
                Type paramType = param.primaryType().type;
                paramIDs.add(entryFac.createFromType(paramName, paramType));
            }

            FuncEntry entry = entryFac.createFunctionEntry(name, ctx.type, paramIDs);
            this.addToScope(ctx, name, entry);
            this.functions.put(name, entry);
        }

        exitScope();
    }

    @Override
    public void exitParam(MinespeakParser.ParamContext ctx) {
        String name = ctx.ID().getText();
        ctx.type = ctx.primaryType().type;
        if (ctx.type == Type._void || ctx.)
        this.addToScope(ctx, name, entryFac.createFromType(name, ctx.type));
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
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
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(logFac.createTypeError(ctx.expr().getText(), ctx.expr(), ctx.expr().type, Type._bool));
        }
        System.out.println(ctx.getSourceInterval().b);
        exitScope();
    }

    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterForeach(MinespeakParser.ForeachContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
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
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterIfStmnt(MinespeakParser.IfStmntContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterBody(MinespeakParser.BodyContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    @Override
    public void exitBody(MinespeakParser.BodyContext ctx) {
        exitScope();
    }

    @Override
    public void exitDcls(MinespeakParser.DclsContext ctx) {
        addMultipleToScope(ctx);
    }

    @Override
    public void exitInstan(MinespeakParser.InstanContext ctx) {
        addMultipleToScope(ctx);
    }

    @Override
    public void enterArrayAccess(MinespeakParser.ArrayAccessContext ctx) {
        Type tempType = this.lookupTypeInScope(ctx, ctx.ID().getText());
        if (!(tempType instanceof ArrayType))
            Logger.shared.add(logFac.createVarNotArrayLog(ctx.ID().getText(), ctx));
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
        else {
            ctx.type = Type._error;
            Logger.shared.add(logFac.createTypeError(ctx.getText(), ctx, ctx.type, ctx.type));
        }
    }

    @Override
    public void exitAddSub(MinespeakParser.AddSubContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if(ctx.ADD() != null){
            ctx.type = Type.inferType(left, MinespeakParser.ADD, right);
        } else if (ctx.SUB() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.SUB, right);
        }
    }

    @Override
    public void exitPow(MinespeakParser.PowContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.POW, right);
    }

    @Override
    public void exitOr(MinespeakParser.OrContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.OR, right);
    }

    @Override
    public void exitAnd(MinespeakParser.AndContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.AND, right);
    }

    @Override
    public void exitEquality(MinespeakParser.EqualityContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.EQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.EQUAL, right);
        } else if(ctx.NOTEQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.NOTEQUAL, right);
        }

    }

    @Override
    public void exitRelations(MinespeakParser.RelationsContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.LESSER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSER, right);
        } else if(ctx.GREATER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATER, right);
        } else if(ctx.LESSEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSEQ, right);
        } else if(ctx.GREATEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATEQ, right);
        }
    }

    @Override
    public void exitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        super.exitMulDivMod(ctx);
    }

    @Override
    public void exitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.rvalue() != null) {
            ctx.type = lookupTypeInScope(ctx, ctx.rvalue().ID().getText());
        } else if (ctx.expr() != null) {
            ctx.type = ctx.expr().type;
        } else if (ctx.literal() != null) {
            ctx.type = ctx.literal().type;
        } else if (ctx.funcCall() != null) {
            ctx.type = ctx.funcCall().type;
        } else if (ctx.arrayAccess() != null) {
            ctx.type = ctx.arrayAccess().type;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void exitLiteral(MinespeakParser.LiteralContext ctx) {
        if(ctx.booleanLiteral() != null) {
            ctx.type = Type._bool;
        } else if(ctx.BlockLiteral() != null) {
            ctx.type = Type._block;
        } else if(ctx.numberLiteral() != null) {
            ctx.type = Type._num;
        } else if(ctx.StringLiteral() != null) {
            ctx.type = Type._num;
        } else if(ctx.vector2Literal() != null) {
            ctx.type = Type._vector2;
        } else if(ctx.vector3Literal() != null) {
            ctx.type = Type._vector3;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void exitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        ctx.type = ctx.factor().type;
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    private void addToScope(ParserRuleContext ctx, String key, SymEntry var) {
        if (!this.currentScope.addVariable(key, var)) {
            Logger.shared.add(logFac.createDuplicateVarLog(key, ctx));
        }
    }

    private Type lookupTypeInScope(ParserRuleContext ctx, String key) {
        SymEntry entry = this.currentScope.lookup(key);

        if (entry == null)
            Logger.shared.add(logFac.createNotDeclaredLog(key, ctx));

        return entry == null ? Type._error : entry.getType();
    }

    private void addMultipleToScope(ParserRuleContext ctx) {
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

    private void resetFunctions() {
        functions.clear();
    }
}
