import templates.MCStatementST;
import templates.STTest;

public class CodeGenVisitor extends MinespeakBaseVisitor{

    @Override
    public Object visitProg(MinespeakParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public Object visitBlocks(MinespeakParser.BlocksContext ctx) {
        return super.visitBlocks(ctx);
    }

    @Override
    public Object visitBlock(MinespeakParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public Object visitMcFunc(MinespeakParser.McFuncContext ctx) {
        return super.visitMcFunc(ctx);
    }

    @Override
    public Object visitFunc(MinespeakParser.FuncContext ctx) {
        return super.visitFunc(ctx);
    }

    @Override
    public Object visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        return super.visitFuncSignature(ctx);
    }

    @Override
    public Object visitParams(MinespeakParser.ParamsContext ctx) {
        return super.visitParams(ctx);
    }

    @Override
    public Object visitParam(MinespeakParser.ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public Object visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        return super.visitFuncBody(ctx);
    }

    @Override
    public Object visitRetVal(MinespeakParser.RetValContext ctx) {
        return super.visitRetVal(ctx);
    }

    @Override
    public Object visitStmnts(MinespeakParser.StmntsContext ctx) {
        return super.visitStmnts(ctx);
    }

    @Override
    public Object visitStmnt(MinespeakParser.StmntContext ctx) {
        String command = ctx.MCStmnt().getText().replace("$", "");
        MCStatementST returnString = new MCStatementST(command);
        return super.visitStmnt(ctx);
    }

    @Override
    public Object visitLoop(MinespeakParser.LoopContext ctx) {
        return super.visitLoop(ctx);
    }

    @Override
    public Object visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        return super.visitDoWhile(ctx);
    }

    @Override
    public Object visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        return super.visitWhileStmnt(ctx);
    }

    @Override
    public Object visitForeach(MinespeakParser.ForeachContext ctx) {
        return super.visitForeach(ctx);
    }

    @Override
    public Object visitForeachInit(MinespeakParser.ForeachInitContext ctx) {
        return super.visitForeachInit(ctx);
    }

    @Override
    public Object visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        return super.visitForStmnt(ctx);
    }

    @Override
    public Object visitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        return super.visitIfStmnt(ctx);
    }

    @Override
    public Object visitBody(MinespeakParser.BodyContext ctx) {
        return super.visitBody(ctx);
    }

    @Override
    public Object visitModifiers(MinespeakParser.ModifiersContext ctx) {
        return super.visitModifiers(ctx);
    }

    @Override
    public Object visitDcls(MinespeakParser.DclsContext ctx) {
        return super.visitDcls(ctx);
    }

    @Override
    public Object visitInstan(MinespeakParser.InstanContext ctx) {
        return super.visitInstan(ctx);
    }

    @Override
    public Object visitInitialValue(MinespeakParser.InitialValueContext ctx) {
        return super.visitInitialValue(ctx);
    }

    @Override
    public Object visitRArray(MinespeakParser.RArrayContext ctx) {
        return super.visitRArray(ctx);
    }

    @Override
    public Object visitArrayAccess(MinespeakParser.ArrayAccessContext ctx) {
        return super.visitArrayAccess(ctx);
    }

    @Override
    public Object visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public Object visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        return super.visitNotNegFac(ctx);
    }

    @Override
    public Object visitOr(MinespeakParser.OrContext ctx) {
        return super.visitOr(ctx);
    }

    @Override
    public Object visitAddSub(MinespeakParser.AddSubContext ctx) {
        return super.visitAddSub(ctx);
    }

    @Override
    public Object visitAnd(MinespeakParser.AndContext ctx) {
        return super.visitAnd(ctx);
    }

    @Override
    public Object visitPow(MinespeakParser.PowContext ctx) {
        return super.visitPow(ctx);
    }

    @Override
    public Object visitRelations(MinespeakParser.RelationsContext ctx) {
        return super.visitRelations(ctx);
    }

    @Override
    public Object visitEquality(MinespeakParser.EqualityContext ctx) {
        return super.visitEquality(ctx);
    }

    @Override
    public Object visitFactor(MinespeakParser.FactorContext ctx) {
        return super.visitFactor(ctx);
    }

    @Override
    public Object visitRvalue(MinespeakParser.RvalueContext ctx) {
        return super.visitRvalue(ctx);
    }

    @Override
    public Object visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }
}
