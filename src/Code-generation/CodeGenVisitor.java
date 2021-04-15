
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenVisitor extends MinespeakBaseVisitor<Value>{
    private Scope currentScope;
    private Map<String, FuncEntry> funcSignature;
    private MSValueFactory msValueFactory = new MSValueFactory();
    private STemplateFactory templateFactory = new STemplateFactory();
    private boolean PosRelativeToPlayer = true;

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    @Override
    public Value visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {

        boolean factorBool= false;
        int factorNum= 0;
        Vector2 factorVec2 = null;
        Vector3 factorVec3 = null;
        var a = visit(ctx.factor());

        if (ctx.type == Type._bool)
            factorBool = Value.value(visit(ctx.factor()).getCasted(BoolValue.class));
        else if (ctx.type == Type._num)
            factorNum = Value.value(visit(ctx.factor()).getCasted(NumValue.class));
        else if (ctx.type == Type._vector2)
            factorVec2 = Value.value(visit(ctx.factor()).getCasted(Vector2Value.class));
        else if (ctx.type == Type._vector3)
            factorVec3 = Value.value(visit(ctx.factor()).getCasted(Vector3Value.class));

        if (ctx.factor().type == Type._bool)
            return msValueFactory.createValue(ctx.NOT() != null ? !factorBool : factorBool, ctx.type);
        else if (ctx.factor().type == Type._num)
            return msValueFactory.createValue(ctx.SUB() != null ? -factorNum : factorNum, ctx.type);
        else if (ctx.factor().type == Type._vector2)
            return msValueFactory.createValue(ctx.SUB() != null ? Vector2.neg(factorVec2) : factorVec2, Type._vector2);
        else if (ctx.factor().type == Type._vector3)
            return msValueFactory.createValue(ctx.SUB() != null ? Vector3.neg(factorVec3) : factorVec3, Type._vector3);

        Error();
        return null;
    }

    @Override
    public Value visitPow(MinespeakParser.PowContext ctx) {
        int num1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class));
        int num2 = Value.value(visit(ctx.expr(1)).getCasted(NumValue.class));
        return msValueFactory.createValue((int) Math.pow(num1, num2), ctx.type);
    }

    @Override
    public Value visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public Value visitAddSub(MinespeakParser.AddSubContext ctx) {
        int num1 = 0, num2 = 0;
        Vector2 vec21 = null, vec22 = null;
        Vector3 vec31 = null, vec32 = null;

        if (ctx.type == Type._num){
            num1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class));
            num2 = Value.value(visit(ctx.expr(1)).getCasted(NumValue.class));
        }
        else if (ctx.type == Type._vector2){
            vec21 = Value.value(visit(ctx.expr(0)).getCasted(Vector2Value.class));
            vec22 = Value.value(visit(ctx.expr(1)).getCasted(Vector2Value.class));
        }
        else if (ctx.type == Type._vector3){
            vec31 = Value.value(visit(ctx.expr(0)).getCasted(Vector3Value.class));
            vec32 = Value.value(visit(ctx.expr(1)).getCasted(Vector3Value.class));
        }

        if (ctx.ADD() != null)
            if (ctx.type == Type._num)
                return msValueFactory.createValue(num1 + num2, ctx.type);
            else if (ctx.type == Type._vector2)
                return msValueFactory.createValue(Vector2.add(vec21, vec22), ctx.type);
            else if (ctx.type == Type._vector3)
                return msValueFactory.createValue(Vector3.add(vec31, vec32), ctx.type);
        else if (ctx.SUB() != null)
            if (ctx.type == Type._num)
                return msValueFactory.createValue(num1 - num2, ctx.type);
            else if (ctx.type == Type._vector2)
                return msValueFactory.createValue(Vector2.sub(vec21, vec22), ctx.type);
            else if (ctx.type == Type._vector3)
                return msValueFactory.createValue(Vector3.sub(vec31, vec32), ctx.type);

        Error();
        return null;
    }

    @Override
    public Value visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        int radix = ctx.DecimalDigit() != null ? 10 : 16;
        String numText = radix == 10 ? ctx.getText(): ctx.getText().substring(2); // cut '0x' from hex
        return msValueFactory.createValue(Integer.parseInt(numText, radix), Type._num);
    }

    @Override
    public Value visitLiteral(MinespeakParser.LiteralContext ctx) {
        if (ctx.type == Type._string || ctx.type == Type._block)
            return msValueFactory.createValue(ctx.getText(), ctx.type);
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

        Error();
        return null;
    }

    @Override
    public Value visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        return msValueFactory.createValue(ctx.TRUE() != null, Type._bool);
    }

    @Override
    public Value visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        return msValueFactory.createValue(
                new Vector2(
                    Value.value(visit(ctx.expr(0)).getCasted(NumValue.class)),
                    Value.value(visit(ctx.expr(1)).getCasted(NumValue.class))
                ), Type._vector2);
    }

    @Override
    public Value visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        return msValueFactory.createValue(
                new Vector3(
                    Value.value(visit(ctx.expr(0)).getCasted(NumValue.class)),
                    Value.value(visit(ctx.expr(1)).getCasted(NumValue.class)),
                    Value.value(visit(ctx.expr(2)).getCasted(NumValue.class))
                ), Type._vector3);
    }

    @Override
    public Value visitRelations(MinespeakParser.RelationsContext ctx) {
        var expr1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class));
        var expr2 = Value.value(visit(ctx.expr(2)).getCasted(NumValue.class));

        if (ctx.LESSER() != null)
            return msValueFactory.createValue(expr1 < expr2, Type._bool);
        else if (ctx.GREATER() != null)
            return msValueFactory.createValue(expr1 > expr2, Type._bool);
        else if (ctx.LESSEQ() != null)
            return msValueFactory.createValue(expr1 <= expr2, Type._bool);
        else if (ctx.GREATEQ() != null)
            return msValueFactory.createValue(expr1 >= expr2, Type._bool);

        Error();
        return null;
    }

    @Override
    public Value visitEquality(MinespeakParser.EqualityContext ctx) {
        var expr1 = visit(ctx.expr(0));
        var expr2 = visit(ctx.expr(2));

        if (ctx.EQUAL() != null)
            return msValueFactory.createValue(expr1 == expr2, Type._bool);
        else if (ctx.NOTEQUAL() != null)
            return msValueFactory.createValue(expr1 != expr2, Type._bool);
        else
            Error();

        return null;
    }


    @Override
    public Value visitAnd(MinespeakParser.AndContext ctx) {
        Value v1 = visit(ctx.expr(0));

        var b1 = Value.value(v1.getCasted(BoolValue.class));
        Value v2 = visit(ctx.expr(0));
        var b2 = Value.value(v2.getCasted(BoolValue.class));

        return msValueFactory.createValue((b1 && b2), Type._bool);
    }

    @Override
    public Value visitOr(MinespeakParser.OrContext ctx) {
        Value v1 = visit(ctx.expr(0));
        var b1 = Value.value(v1.getCasted(BoolValue.class));
        Value v2 = visit(ctx.expr(0));
        var b2 = Value.value(v2.getCasted(BoolValue.class));

        return msValueFactory.createValue((b1 || b2), Type._bool);
    }

    @Override
    public Value visitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.LPAREN() != null)
            return visit(ctx.expr());
        else if (ctx.rvalue() != null)
            return visit(ctx.rvalue());
        else if (ctx.literal() != null)
            return visit(ctx.literal());
        else if (ctx.funcCall() != null)
            return visit(ctx.funcCall());
        else
            return visit(ctx.arrayAccess());
    }

    @Override
    public Value visitRvalue(MinespeakParser.RvalueContext ctx) {
        return currentScope.lookup(ctx.ID().getText()).getValue();
    }

    @Override
    public Value visitProg(MinespeakParser.ProgContext ctx) {
        currentScope = ctx.scope;
        return super.visitProg(ctx);
    }

    @Override
    public Value visitFunc(MinespeakParser.FuncContext ctx) {
        currentScope = ctx.scope;
        var id = ctx.funcSignature().ID().getText();
        var func = funcSignature.getOrDefault(id, null);

        Value a = visit(ctx.funcBody());
        func.setValue(a);
        return null;
    }

    @Override
    public Value visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        currentScope = ctx.scope;
        return super.visitFuncBody(ctx);
    }

    @Override
    public Value visitStmnts(MinespeakParser.StmntsContext ctx) {

        String stmntsText = "";

        for (var child:ctx.children) {
            var b = visit(child);
            stmntsText += (b != null ? Value.value(b.getCasted(StringValue.class)) : "");
        }

        return msValueFactory.createValue(stmntsText, Type._string);
    }

    @Override
    public Value visitStmnt(MinespeakParser.StmntContext ctx) {
        String command = "";
        if (ctx.MCStmnt() != null) {
            return visitMCStmnt(ctx);
        } else {
            return super.visitStmnt(ctx);
        }
    }

    @Override
    public Value visitInstan(MinespeakParser.InstanContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++) {
            String ID = ctx.ID(i).getText();


            var expr = ctx.initialValue(i).expr();
            Value exprEval = visit(expr);

            if (expr.type == Type._num) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(NumValue.class)));
                return msValueFactory.createValue(templateFactory.createInstanST(lookup).getOutput(), Type._string);
//                return msValueFactory.createValue(templateFactory.createInstanST(ID,Value.value(exprEval.getCasted(NumValue.class)).toString()), Type._string);
            }
            else if (expr.type == Type._block)
                currentScope.lookup(ID).setValue(Value.value(exprEval.getCasted(BlockValue.class)));
            else if (expr.type == Type._bool) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(BoolValue.class)));
                return msValueFactory.createValue(templateFactory.createInstanST(lookup).getOutput(), Type._string);
            }
            else if (expr.type == Type._string)
                currentScope.lookup(ID).setValue(Value.value(exprEval.getCasted(StringValue.class)));
            else if (expr.type == Type._vector2) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(Vector2Value.class)));
                return msValueFactory.createValue(templateFactory.createInstanST(lookup).getOutput(), Type._string);
            }
            else if (expr.type == Type._vector3) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(Vector3Value.class)));
                return msValueFactory.createValue(templateFactory.createInstanST(lookup).getOutput(), Type._string);
            }
        }

        return null;
    }

    @Override
    public Value visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        var expr = ctx.expr(0).getText();
        var func = funcSignature.getOrDefault(ctx.ID().getText(), null);

//        for (int i = 0; i < func.getParams().size(); i++) {
//            var val = visit(ctx.expr(i));
//            currentScope.lookup(func.getParams().get(i).getName()).setValue(2);
//        }
//        ctx.expr()
        if (func == null)
            return null;

        System.out.println(Value.value(func.getValue().getCasted(StringValue.class)));
        return msValueFactory.createValue(func.getValue().toString(), Type._string);
    }

    /*
     Helper
     */

    private Value visitMCStmnt(MinespeakParser.StmntContext ctx) {
        String stmnt = ctx.MCStmnt().getText();
        return msValueFactory.createValue(new MCStatementST(formatString(stmnt)).getOutput(), Type._string);
    }

    private String formatString(String stmnt) {
        String command;
        Pattern varReplacePattern = Pattern.compile("(?<prefix>\\~|\\^)?v\\{(?<varName>\\w)*\\}");
        Matcher matcher = varReplacePattern.matcher(stmnt);

        while (matcher.find()) {
            String prefix = matcher.group("prefix");
            prefix = prefix != null ? prefix : "";
            String varName = matcher.group("varName");
            Type type = currentScope.lookup(varName).getType();
            Value varVal = currentScope.lookup(varName).getValue();

            String formatString ="";

            if (type == Type._num)
                formatString = prefix + Value.value(varVal.getCasted(NumValue.class)).toString();
            else if (type == Type._block)
                formatString = prefix + Value.value(varVal.getCasted(BlockValue.class));
            else if (type == Type._bool)
                formatString = prefix + Value.value(varVal.getCasted(BoolValue.class)).toString();
            else if (type == Type._string)
                formatString = prefix + Value.value(varVal.getCasted(StringValue.class));
            else if (type == Type._vector2)
                formatString = Value.value(varVal.getCasted(Vector2Value.class)).toString(prefix != "" ? prefix : "~");
            else if (type == Type._vector3)
                formatString = Value.value(varVal.getCasted(Vector3Value.class)).toString(prefix != "" ? prefix : "~");
            else
                Error();

            stmnt = stmnt.replace(prefix + "v{" + varName + "}", formatString);
        }

        command = stmnt.replace("$", "");
        return command;
    }

    private void Error(){
        System.out.println("SHOULD NOT HAPPEN!");
    }
}
