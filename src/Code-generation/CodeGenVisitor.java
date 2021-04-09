import org.antlr.v4.runtime.ParserRuleContext;
import templates.MCStatementST;
import templates.STTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenVisitor extends MinespeakBaseVisitor{
    private Scope currentScope;
    private Map<String, FuncEntry> funcSignature;
    private final EntryFactory entryFac = new EntryFactory();
    private final ScopeFactory scopeFac = new ScopeFactory();

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    @Override
    public Object visitProg(MinespeakParser.ProgContext ctx) {
        currentScope = ctx.scope;
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
        currentScope = ctx.scope;
        var id = ctx.funcSignature().ID().getText();
        var func = funcSignature.getOrDefault(id, null);

        String a = visit(ctx.funcBody()).toString();
        func.setValue(a);
//        func.setValue("test");

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
        currentScope = ctx.scope;
        return super.visitFuncBody(ctx);
    }

    @Override
    public Object visitRetVal(MinespeakParser.RetValContext ctx) {
        return super.visitRetVal(ctx);
    }

    @Override
    public Object visitStmnts(MinespeakParser.StmntsContext ctx) {

        String a = "";

        for (var child:ctx.children) {
            var b = visit(child);
            a += (b != null ? b : "");
        }

        return a;
    }

    @Override
    public Object visitStmnt(MinespeakParser.StmntContext ctx) {
        String command = "";
        if (ctx.MCStmnt() != null) {
            String stmnt = ctx.MCStmnt().getText();

            Pattern varReplacePattern = Pattern.compile("v\\{(?<varName>\\w)*\\}");
            Matcher matcher = varReplacePattern.matcher(stmnt);

            while (matcher.find()) {
                String varName = matcher.group("varName");
                var varVal = currentScope.lookup(varName);
                stmnt = stmnt.replace("v{" + varName + "}", varVal != null ? varVal.getValue().toString() : "MAKELOOKUP");
            }

            command = stmnt.replace("$", "");
            super.visitStmnt(ctx);
            return new MCStatementST(command).output;
        }else {
            for (var child:ctx.children) {
                visit(child);
            }
            return "super.visitStmnt(ctx)";
        }
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
        for (int i = 0; i < ctx.ID().size(); i++) {
            String ID = ctx.ID(i).getText();

            currentScope.lookup(ID).setValue(calcNumExpression(ctx.initialValue(i).expr()));
        }
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
        var expr = ctx.expr(0).getText();
        var func = funcSignature.getOrDefault(ctx.ID().getText(), null);

        if (func == null) return null;
        System.out.println(func.getValue().toString());

        return null;
        //return super.visitFuncCall(ctx);
    }




    public int calcNumExpression(MinespeakParser.ExprContext ctx) {
        return 1;
    }
}
