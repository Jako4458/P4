
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenVisitor extends MinespeakBaseVisitor<Value>{
    private Scope currentScope;
    private FuncEntry currentFunc;
    private final Map<String, FuncEntry> funcSignature;
    private final MSValueFactory msValueFactory = new MSValueFactory();
    private final STemplateFactory templateFactory = new STemplateFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();

    private ArrayList<Template> output = new ArrayList<>();

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    @Override
    public Value visitProg(MinespeakParser.ProgContext ctx) {
        currentScope = ctx.scope;

        output.add(templateFactory.createMCStatementST("scoreboard players reset @s")); // for debug

        output.add(templateFactory.createDclST(templateFactory.factor1UUID, Type._num));
        output.add(templateFactory.createDclST(templateFactory.factor2UUID, Type._num));
        output.add(templateFactory.createDclST(templateFactory.vecFactor1UUID, Type._vector3));
        output.add(templateFactory.createDclST(templateFactory.vecFactor2UUID, Type._vector3));

        for (FuncEntry func:this.funcSignature.values()) {
            visit(func.getCtx().parent);
        }
//        super.visitProg(ctx);

        for (FuncEntry func:funcSignature.values()) {
            if (func.isMCFunction()) {
                recompileFunction(func);
                output.add(templateFactory.createFuncCallST(func));
            }
        }

        for (Template t:output) {
            System.out.println(t.getOutput());
        }
        return null;
    }

    @Override
    public Value visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        String exprName = null;
        if (ctx.factor().rvalue() != null){
            exprName = ctx.factor().rvalue().ID().getText();
            exprName = currentScope.lookup(exprName) == null ? exprName : currentScope.lookup(exprName).getVarName();
            factorNameTable.put(ctx, exprName);
        }

        boolean factorBool;
        int factorNum;
        Vector2 factorVec2;
        Vector3 factorVec3;
        var factor = visit(ctx.factor());

        exprName = exprName != null ? exprName : templateFactory.factor1UUID;


        if (ctx.type == Type._bool){
            factorBool = Value.value(factor.getCasted(BoolValue.class));
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, factorBool ? 1 : 0));
            if (ctx.NOT() != null)
                currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "not", Type._bool));
            else{
                currentFunc.addTemplate(templateFactory.createInstanST(exprName));
            }
            return msValueFactory.createValue((ctx.NOT() == null) == factorBool, ctx.type);
        }
        else if (ctx.type == Type._num) {
            factorNum = Value.value(factor.getCasted(NumValue.class));
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, factorNum));
            if (ctx.SUB() != null)
                currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "-", Type._num));
            else{
                currentFunc.addTemplate(templateFactory.createInstanST(exprName));
            }
            return msValueFactory.createValue(ctx.SUB() != null ? -factorNum : factorNum, ctx.type);
        }
        else if (ctx.type == Type._vector2) {
            factorVec2 = Value.value(factor.getCasted(Vector2Value.class));
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, factorVec2));
            if (ctx.SUB() != null)
                currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "-", Type._vector2));
            else{
                currentFunc.addTemplate(templateFactory.createInstanST(exprName));
            }
            return msValueFactory.createValue(ctx.SUB() != null ? Vector2.neg(factorVec2) : factorVec2, Type._vector2);
        }
        else if (ctx.type == Type._vector3) {
            factorVec3 = Value.value(factor.getCasted(Vector3Value.class));
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, factorVec3));
            if (ctx.SUB() != null)
                currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "-", Type._vector3));
            else{
                currentFunc.addTemplate(templateFactory.createInstanST(exprName));
            }
            return msValueFactory.createValue(ctx.SUB() != null ? Vector3.neg(factorVec3) : factorVec3, Type._vector3);
        }

        Error();
        return null;
    }

    @Override
    public Value visitPow(MinespeakParser.PowContext ctx) {
        Integer num1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class)); // a
        Integer num2 = Value.value(visit(ctx.expr(1)).getCasted(NumValue.class));

        var expr1Name = factorNameTable.getOrDefault(ctx.expr(0), templateFactory.factor1UUID);
        var expr2Name = factorNameTable.getOrDefault(ctx.expr(1), templateFactory.factor2UUID);

        currentFunc.addTemplate(templateFactory.createAssignST(expr1Name, num1)); // maybe num.toString()
        currentFunc.addTemplate(templateFactory.createAssignST(expr2Name, num2)); // maybe num.toString()

        expr1Name = expr1Name.equals(templateFactory.factor1UUID) ? templateFactory.factor1UUID : currentScope.lookup(expr1Name).getVarName();
        num2 = expr2Name.equals(templateFactory.factor2UUID) ? num2 :  Value.value(currentScope.lookup(expr2Name).getValue().getCasted(NumValue.class));

        currentFunc.addTemplate(templateFactory.createArithmeticExprST(expr1Name, num2, "Pow"));
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
        return calcLogicalExpr(ctx.expr(0), ctx.expr(1), "and");
    }

    @Override
    public Value visitOr(MinespeakParser.OrContext ctx) {
        return calcLogicalExpr(ctx.expr(0), ctx.expr(1), "or");
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
    public Value visitFunc(MinespeakParser.FuncContext ctx) {
        currentFunc = funcSignature.getOrDefault(ctx.funcSignature().ID().getText(), null);
        currentFunc.scope = ctx.funcBody().scope;
        currentScope = currentFunc.scope;

        visit(ctx.funcBody());
        return null;
    }

    @Override
    public Value visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        if (ctx.stmnts() != null)
            visit(ctx.stmnts());
        if (ctx.retVal() != null)
            currentFunc.setValue(visit(ctx.retVal()));
        return null;
    }

    @Override
    public Value visitRetVal(MinespeakParser.RetValContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Value visitStmnts(MinespeakParser.StmntsContext ctx) {

        for (var child:ctx.children) {
            try {
                visit(child);
            } catch (NullPointerException | NoSuchElementException e) {
                currentFunc.addTemplate(templateFactory.createParamDependantStmntST(child));
            }
        }
        return null;
    }

    @Override
    public Value visitStmnt(MinespeakParser.StmntContext ctx) {
        if (ctx.MCStmnt() != null)
            return visitMCStmnt(ctx);

        return super.visitStmnt(ctx);
    }

    @Override
    public Value visitBody(MinespeakParser.BodyContext ctx) {
        currentScope = ctx.scope;
        return super.visitBody(ctx);
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
                currentFunc.addTemplate(templateFactory.createInstanST(lookup));
            }
            else if (expr.type == Type._block)
                currentScope.lookup(ID).setValue(Value.value(exprEval.getCasted(BlockValue.class)));
            else if (expr.type == Type._bool) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(BoolValue.class)));
                currentFunc.addTemplate(templateFactory.createInstanST(lookup));
            }
            else if (expr.type == Type._string)
                currentScope.lookup(ID).setValue(Value.value(exprEval.getCasted(StringValue.class)));
            else if (expr.type == Type._vector2) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(Vector2Value.class)));
                currentFunc.addTemplate(templateFactory.createInstanST(lookup));
            }
            else if (expr.type == Type._vector3) {
                var lookup = currentScope.lookup(ID);
                lookup.setValue(Value.value(exprEval.getCasted(Vector3Value.class)));
                currentFunc.addTemplate(templateFactory.createInstanST(lookup));
            }
        }

        return null;
    }

    @Override
    public Value visitBlock(MinespeakParser.BlockContext ctx) {
        currentScope = ctx.scope;
        return super.visitBlock(ctx);
    }

    @Override
    public Value visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        var func = funcSignature.getOrDefault(ctx.ID().getText(), null);

        if (!func.isMCFunction()){
            loadParamsToScope(ctx, func);
            recompileFunction(func);
            currentFunc.addTemplate(templateFactory.createFuncCallST(func));
            return func.getValue();
        }
        else {
            recompileFunction(func);
            currentFunc.addTemplate(templateFactory.createMCFuncCallST(func));
        }
        return null;
    }

    /*
     Helper
     */

    private Value visitMCStmnt(MinespeakParser.StmntContext ctx) {
        String stmnt = ctx.MCStmnt().getText();
        currentFunc.addTemplate(templateFactory.createMCStatementST(formatString(stmnt)));
        return null;
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
                formatString = Value.value(varVal.getCasted(Vector2Value.class)).toString(!prefix.equals("") ? prefix : "~");
            else if (type == Type._vector3)
                formatString = Value.value(varVal.getCasted(Vector3Value.class)).toString(!prefix.equals("") ? prefix : "~");
            else
                Error();

            stmnt = stmnt.replace(prefix + "v{" + varName + "}", formatString);
        }

        command = stmnt.replace("$", "");
        return command;
    }

    private void loadParamsToScope(MinespeakParser.FuncCallContext ctx, FuncEntry func) {
        ArrayList<Template> paramList = new ArrayList<>();

        for (int i = 0; i < func.getParams().size(); i++) {
            String paramName = func.scope.lookup(func.getParams().get(i).getName()).getVarName();
            var val = visit(ctx.expr(i));

            if (val.type == Type._num){
                var value = Value.value(val.getCasted(NumValue.class));
                paramList.add(templateFactory.createInstanST(paramName, value));
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(NumValue.class)));

            }
            else if (val.type == Type._block)
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(BlockValue.class)));
            else if (val.type == Type._bool)
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(BoolValue.class)));
            else if (val.type == Type._string)
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(StringValue.class)));
            else if (val.type == Type._vector2)
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(Vector2Value.class)));
            else if (val.type == Type._vector3)
                func.scope.lookup(func.getParams().get(i).getName()).setValue(Value.value(val.getCasted(Vector3Value.class)));
            else
                Error();
        }
    }

    private void recompileFunction(FuncEntry func) {
        var tempScope = currentScope;
        var tempFunc = currentFunc;

        for (Template t: func.getOutput()) {
            if (t instanceof ParameterDependantStmntST) {
                currentScope = func.scope;
                break;
            }
        }
        currentFunc = func;

        var outputSize = func.getOutput().size();
        for (int i = 0; i < outputSize; i++) {
            Template t = func.getOutput().get(i);
            if (t instanceof ParameterDependantStmntST) {
                ParameterDependantStmntST wt = ((ParameterDependantStmntST) t);
                visit(wt.context);  // do stmnt again
                wt.setOutput("");
                while (outputSize < func.getOutput().size()){
                    wt.setOutput(wt.getOutput() + func.getOutput().get(outputSize).getOutput()); // move new output to failed stmnt
                    func.getOutput().remove(outputSize);     // remove new stmnt from end
                }
            }
        }

        currentScope = tempScope;
        currentFunc = tempFunc;
    }

    private Value calcLogicalExpr(MinespeakParser.ExprContext e1, MinespeakParser.ExprContext e2, String operator) {
        Value v1 = visit(e1);
        Value v2 = visit(e2);

        var b1 = Value.value(v1.getCasted(BoolValue.class));
        var b2 = Value.value(v2.getCasted(BoolValue.class));

        var expr1Name = factorNameTable.getOrDefault(e1, templateFactory.factor1UUID);
        var expr2Name = factorNameTable.getOrDefault(e2, templateFactory.factor2UUID);

        if (expr1Name.equals(templateFactory.factor1UUID))
            output.add(templateFactory.createAssignST(expr1Name, b1 ? 1 : 0));
        if (expr2Name.equals(templateFactory.factor2UUID))
            output.add(templateFactory.createAssignST(expr2Name, b2 ? 1 : 0));

        currentFunc.addTemplate(templateFactory.createLogicalExprST(expr1Name, expr2Name, operator));

        if (operator == "and")
            return msValueFactory.createValue((b1 && b2), Type._bool);
        else if (operator == "or")
            return msValueFactory.createValue((b1 || b2), Type._bool);

        Error();
        return null;
    }

    private void Error(){
        System.out.println("SHOULD NOT HAPPEN!");
    }
}
