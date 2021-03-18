import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import utils.Function;
import utils.MSValue;
import utils.Scope;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ScopeVisitor extends MinespeakBaseVisitor<MSValue> {
    private Scope scope;
    private Map<String, Function> functions;

    public ScopeVisitor(Scope scope) {
        this.scope = scope;
        this.functions = new HashMap<>();
    }

    public Map<String, Function> getFunctions() {
        return this.functions;
    }

    public Scope getScope() {
        return this.scope;
    }

    @Override
    public MSValue visitProg(MinespeakParser.ProgContext ctx) {
        return this.visit(ctx.blocks());
    }

    @Override
    public MSValue visitBlocks(MinespeakParser.BlocksContext ctx) {
        ctx.block().forEach(this::visit);   // experimental code, might not work
        return MSValue.VOID;
    }

    @Override
    public MSValue visitBlock(MinespeakParser.BlockContext ctx) {
        if (ctx.func() != null)
            return this.visit(ctx.func());
        else if (ctx.mcFunc() != null)
            return this.visit(ctx.mcFunc());
        else
            return MSValue.ERROR;
    }

    @Override
    public MSValue visitMcFunc(MinespeakParser.McFuncContext ctx) {
        return MSValue.VOID;
    }

    @Override
    public MSValue visitFunc(MinespeakParser.FuncContext ctx) {
        List<TerminalNode> params = new ArrayList<>();
        List<MinespeakParser.ParamContext> rawParams = ctx.params().param();
        for (MinespeakParser.ParamContext param : rawParams) {
            params.add(param.ID());
        }
        ParseTree body = ctx.funcBody();
        String id = ctx.ID().getText();
        functions.put(id, new Function(scope, params, body));

        this.scope = new Scope(this.scope);
        this.visit(ctx.params());
        this.scope = new Scope(this.scope);
        MSValue returnVal = this.visit(ctx.funcBody()); // Used for typechecking later on

        if (ctx.type() == null)
            return MSValue.VOID;
        if (ctx.type().PrimitiveType() == null)
            return MSValue.ERROR;
        return MSValue.generateValueFromType(ctx.type().PrimitiveType().getText());
    }

    @Override
    public MSValue visitParams(MinespeakParser.ParamsContext ctx) {
        ctx.param().forEach(this::visit);
        return MSValue.VOID;
    }

    @Override
    public MSValue visitParam(MinespeakParser.ParamContext ctx) {
        this.scope.addVariable(ctx.ID().getText(), MSValue.generateValueFromType(ctx.type().getText()));
        return MSValue.VOID;
    }

    @Override
    public MSValue visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        this.visit(ctx.stmnts());
        if (ctx.retVal() == null)
            return MSValue.VOID;
        return this.visit(ctx.retVal());
    }

    @Override
    public MSValue visitRetVal(MinespeakParser.RetValContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public MSValue visitStmnts(MinespeakParser.StmntsContext ctx) {
        ctx.stmnt().forEach(this::visit);
        return MSValue.VOID;
    }

    @Override
    public MSValue visitDclsStmnt(MinespeakParser.DclsStmntContext ctx) {
        return this.visit(ctx.dcls());
    }

    @Override
    public MSValue visitAssignStmnt(MinespeakParser.AssignStmntContext ctx) {
        return this.visit(ctx.assign());
    }

    @Override
    public MSValue visitInstanStmnt(MinespeakParser.InstanStmntContext ctx) {
        return this.visit(ctx.instan());
    }

    @Override
    public MSValue visitIfStmntStmnt(MinespeakParser.IfStmntStmntContext ctx) {
        



        return this.visit(ctx.ifStmnt());
    }

    @Override
    public MSValue visitLoopStmnt(MinespeakParser.LoopStmntContext ctx) {
        return this.visit(ctx.loop());
    }

    @Override
    public MSValue visitMCStmntStmnt(MinespeakParser.MCStmntStmntContext ctx) {
        return this.visit(ctx.MCStmnt());
    }

    @Override
    public MSValue visitFuncCallStmnt(MinespeakParser.FuncCallStmntContext ctx) {

        return this.visit(ctx.funcCall());
    }

    @Override
    public MSValue visitLoop(MinespeakParser.LoopContext ctx) {
        return super.visitLoop(ctx);
    }

    @Override
    public MSValue visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        return super.visitDoWhile(ctx);
    }

    @Override
    public MSValue visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        return super.visitWhileStmnt(ctx);
    }

    @Override
    public MSValue visitForeach(MinespeakParser.ForeachContext ctx) {
        return super.visitForeach(ctx);
    }

    @Override
    public MSValue visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        return super.visitForStmnt(ctx);
    }

    @Override
    public MSValue visitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        return super.visitIfStmnt(ctx);
    }

    @Override
    public MSValue visitDcls(MinespeakParser.DclsContext ctx) {
        return super.visitDcls(ctx);
    }

    @Override
    public MSValue visitInstan(MinespeakParser.InstanContext ctx) {
        System.out.println("I got here");
        for (int i = 0; i < ctx.ID().size(); i++) {
            scope.addVariable(ctx.ID(i).getText(), this.visit(ctx.expr(i)));
        }
        return MSValue.generateValueFromType(ctx.type().PrimitiveType().getText());
    }

    @Override
    public MSValue visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public MSValue visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        return super.visitNotNegFac(ctx);
    }

    @Override
    public MSValue visitOr(MinespeakParser.OrContext ctx) {
        return super.visitOr(ctx);
    }

    @Override
    public MSValue visitAddSub(MinespeakParser.AddSubContext ctx) {

        return super.visitAddSub(ctx);
    }

    @Override
    public MSValue visitAnd(MinespeakParser.AndContext ctx) {
        return super.visitAnd(ctx);
    }

    @Override
    public MSValue visitPow(MinespeakParser.PowContext ctx) {
        return super.visitPow(ctx);
    }

    @Override
    public MSValue visitRelations(MinespeakParser.RelationsContext ctx) {
        return super.visitRelations(ctx);
    }

    @Override
    public MSValue visitEquality(MinespeakParser.EqualityContext ctx) {
        return super.visitEquality(ctx);
    }

    @Override
    public MSValue visitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.literal() != null)
            return new MSValue(ctx.literal().getText());
        return new MSValue(ctx.expr());
    }

    @Override
    public MSValue visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    @Override
    public MSValue visitAssign(MinespeakParser.AssignContext ctx) {
        return super.visitAssign(ctx);
    }

    @Override
    public MSValue visitType(MinespeakParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public MSValue visitLiteral(MinespeakParser.LiteralContext ctx) {
        return super.visitLiteral(ctx);
    }

    @Override
    public MSValue visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        return super.visitNumberLiteral(ctx);
    }

    @Override
    public MSValue visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        return super.visitVector2Literal(ctx);
    }

    @Override
    public MSValue visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        return super.visitVector3Literal(ctx);
    }

    @Override
    public MSValue visitNewlines(MinespeakParser.NewlinesContext ctx) {
        return MSValue.VOID;
    }
}
