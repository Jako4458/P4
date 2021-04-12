import templates.MCStatementST;

import javax.management.ValueExp;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenVisitor extends MinespeakBaseVisitor{
    private Scope currentScope;
    private Map<String, FuncEntry> funcSignature;
    private MSValueFactory msValueFactory = new MSValueFactory();

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    @Override
    public Object visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {

        var factorObj = visit(ctx.factor());

        if (ctx.factor().type == Type._bool) {
            boolean factor = (boolean) factorObj;
            return ctx.NOT() != null ? !factor : factor;
        }
        else if (ctx.factor().type == Type._num){
            int factor = (int) factorObj;
            return ctx.SUB() != null ? -factor : factor;
        }

         return factorObj;
    }

    @Override
    public Object visitPow(MinespeakParser.PowContext ctx) {
        return Math.pow((double) visit(ctx.expr(0)), (double) visit(ctx.expr(1)));
    }

    @Override
    public Object visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public Object visitAddSub(MinespeakParser.AddSubContext ctx) {
        int num1 = (int) visit(ctx.expr(0));
        int num2 = (int) visit(ctx.expr(1));

        if (ctx.ADD() != null)
            return num1 + num2;
        else if (ctx.SUB() != null)
            return num1 - num2;
        else
            Error();

        return null;
    }

    @Override
    public Object visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        int radix = ctx.DecimalDigit() != null ? 10 : 16;
        String numText = radix == 10 ? ctx.getText(): ctx.getText().substring(2); // cut '0x' from hex
        return Integer.parseInt(numText, radix);
    }

    @Override
    public Object visitLiteral(MinespeakParser.LiteralContext ctx) {
        if (ctx.type == Type._string || ctx.type == Type._block)
            return ctx.getText();
        else if (ctx.type == Type._num)
            return visit(ctx.numberLiteral());
        else if (ctx.type == Type._bool)
            return visit(ctx.booleanLiteral());
        else if (ctx.type == Type._vector2)
            return visit(ctx.vector2Literal());
        else if (ctx.type == Type._vector3)
            return visit(ctx.vector3Literal());
        else if (ctx.rArray() != null)
            return visit(ctx.rArray());
        else
            Error();

        return null;
    }

    @Override
    public Object visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        return ctx.TRUE() != null;
    }

    @Override
    public Object visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        return getVector(ctx.vecElement());
    }

    @Override
    public Object visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        return getVector(ctx.vecElement());
    }

    @Override
    public Object visitRelations(MinespeakParser.RelationsContext ctx) {
        var expr1 = visit(ctx.expr(0));
        var expr2 = visit(ctx.expr(2));

        if (ctx.LESSER() != null)
            return (int) expr1 < (int) expr2;
        else if (ctx.GREATER() != null)
            return (int) expr1 > (int) expr2;
        else if (ctx.LESSEQ() != null)
            return (int) expr1 <= (int) expr2;
        else if (ctx.GREATEQ() != null)
            return (int) expr1 >= (int) expr2;
        else
            Error();

        return null;
    }

    @Override
    public Object visitEquality(MinespeakParser.EqualityContext ctx) {
        var expr1 = visit(ctx.expr(0));
        var expr2 = visit(ctx.expr(2));

        if (ctx.EQUAL() != null)
            return expr1 == expr2;
        else if (ctx.NOTEQUAL() != null)
            return expr1 != expr2;
        else
            Error();

        return null;
    }


    @Override
    public Object visitAnd(MinespeakParser.AndContext ctx) {
        return (boolean) visit(ctx.expr(0)) && (boolean) visit(ctx.expr(1));
    }

    @Override
    public Object visitOr(MinespeakParser.OrContext ctx) {
        return (boolean) visit(ctx.expr(0)) || (boolean) visit(ctx.expr(1));
    }

    @Override
    public Object visitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.LPAREN() != null)
            return visit(ctx.expr());
        else if (ctx.rvalue() != null)
            return visit(ctx.rvalue());
        else if (ctx.literal() != null)
            return visit(ctx.literal());
        else if (ctx.funcCall() != null)
            return ctx.funcCall();
        else
            return visit(ctx.arrayAccess());
    }

    @Override
    public Object visitProg(MinespeakParser.ProgContext ctx) {
        currentScope = ctx.scope;
        return super.visitProg(ctx);
    }

    @Override
    public Object visitFunc(MinespeakParser.FuncContext ctx) {
        currentScope = ctx.scope;
        var id = ctx.funcSignature().ID().getText();
        var func = funcSignature.getOrDefault(id, null);

        String a = visit(ctx.funcBody()).toString();
        func.setValue(a);
        return null;
    }

    @Override
    public Object visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        currentScope = ctx.scope;
        return super.visitFuncBody(ctx);
    }

    @Override
    public Object visitStmnts(MinespeakParser.StmntsContext ctx) {

        String stmntsText = "";

        for (var child:ctx.children) {
            var b = visit(child);
            stmntsText += (b != null ? b : "");
        }

        return stmntsText;
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
            return super.visitStmnt(ctx);
        }
    }

    @Override
    public Object visitInstan(MinespeakParser.InstanContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++) {
            String ID = ctx.ID(i).getText();


            String exprEval = visit(ctx.initialValue(i).expr()).toString();
            currentScope.lookup(ID).setValue(exprEval);

        }

        return null;
    }

    @Override
    public Object visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        var expr = ctx.expr(0).getText();
        var func = funcSignature.getOrDefault(ctx.ID().getText(), null);

        if (func == null)
            return null;

        System.out.println(func.getValue().toString());
        return msValueFactory.createValue(func.getValue().toString(), Type._string);
    }



    /*
     Helper
     */

    private Vector<Integer> getVector(List<MinespeakParser.VecElementContext> elements) {
        Vector<Integer> vector = new Vector<>();

        for (var element: elements) {
            vector.add((int)visit(element));
        }
        return vector;
    }

    private void Error(){
        System.out.println("SHOULD NOT HAPPEND!");
    }
}
