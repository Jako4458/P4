
import Logging.Logger;
import exceptions.CompileTimeException;
import exceptions.FuncCompileDependantException;
import exceptions.ParameterDependantException;
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
    private final LogFactory logFactory = new LogFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();

    private final ArrayList<String> prefixs = new ArrayList<>();

    private final ArrayList<Template> output = new ArrayList<>();

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    private String getPrefix() {
        StringBuilder builder = new StringBuilder();
        for (String string : prefixs) {
            builder.append(string);
        }
        return builder.toString();
    }

    @Override
    public Value visitProg(MinespeakParser.ProgContext ctx) {
        currentScope = ctx.scope;
        makeProgramHeaders(true);   // set to false when not debugging

        for (FuncEntry func:this.funcSignature.values()) {
            try {
                visit(func.getCtx().parent);
            } catch (CompileTimeException e) {
                return null;
            }
        }

        for (FuncEntry func:funcSignature.values()) {
            if (func.isMCFunction()) {
                try {
                    recompileFunction(func);
                } catch (CompileTimeException e) {
                    return null;
                }
                output.add(templateFactory.createFuncCallST(func));
            }
        }

        makeProgramFooters();
        printOutput();
        return null;
    }

    private void printOutput() {
        for (Template t:output) {
            System.out.println(t.getOutput());
        }
    }

    private void makeProgramHeaders(boolean debug) {
        if (debug)
            output.add(templateFactory.createMCStatementST("scoreboard players reset @s", "")); // for debug

        output.add(templateFactory.createDclST(templateFactory.factor1UUID, Type._num, ""));
        output.add(templateFactory.createDclST(templateFactory.factor2UUID, Type._num, ""));
        output.add(templateFactory.createDclST(templateFactory.factor1UUID, Type._vector3, ""));
        output.add(templateFactory.createDclST(templateFactory.factor2UUID, Type._vector3, ""));
        output.add(templateFactory.createDclST("BlockFactor1", Type._block, ""));
        output.add(templateFactory.createDclST("BlockFactor2", Type._block, ""));
    }

    private void makeProgramFooters() {
        output.add(new BlankST("execute as @e[tag=variable] at @e[tag=variable] run setblock ~ ~-1 ~ air"));
        output.add(new BlankST("kill @e[tag=variable]")); 
    }
  
    @Override
    public Value visitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        prefixs.add("");
        visit(ctx.expr(0));
        String curPrefix = prefixs.get(prefixs.size() - 1);
        curPrefix = curPrefix.replace("matches 1", "matches 0");
        curPrefix = curPrefix.concat("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
        prefixs.set(prefixs.size() - 1, curPrefix);
        visit(ctx.body(0));
        for (int i = 1; i < ctx.expr().size(); i++) {
            curPrefix = prefixs.get(prefixs.size() - 1);
            curPrefix = curPrefix.replace("matches 1", "matches 0");
            prefixs.set(prefixs.size() - 1, curPrefix);
            visit(ctx.expr(i));
            curPrefix = curPrefix.concat("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
            prefixs.set(prefixs.size() - 1, curPrefix);
            visit(ctx.body(i));
        }

        if (ctx.ELSE() != null)
            prefixs.set(prefixs.size() - 1, prefixs.get(prefixs.size() - 1).replace("matches 1", "matches 0"));

        visit(ctx.body(ctx.body().size() - 1));

        prefixs.remove(prefixs.size() - 1);
        return null;
    }

    @Override
    public Value visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        String exprName = null;
        if (ctx.factor().rvalue() != null){
            exprName = ctx.factor().rvalue().ID().getText();
            exprName = currentScope.lookup(exprName) == null ? exprName : currentScope.lookup(exprName).getVarName();
        }

        boolean factorBool;
        int factorNum;
        Vector2 factorVec2;
        Vector3 factorVec3;
        Value factor = visit(ctx.factor());

        String lookup = factorNameTable.getOrDefault(ctx.factor(), null);
        exprName = exprName != null ? exprName : lookup != null ? lookup :templateFactory.factor1UUID;

        switch (ctx.type.getTypeAsInt()) {
            case Type.BLOCK:
            case Type.STRING:
                return factor;
            case Type.BOOL:
                factorBool = Value.value(factor.getCasted(BoolValue.class));
                generateTemplateSub(ctx, exprName, lookup, factorBool, Type._bool);
                return msValueFactory.createValue((ctx.NOT() == null) == factorBool, ctx.type);
            case Type.NUM:
                factorNum = Value.value(factor.getCasted(NumValue.class));
                generateTemplateSub(ctx, exprName, lookup, factorNum, Type._num);
                return msValueFactory.createValue(ctx.SUB() != null ? -factorNum : factorNum, ctx.type);
            case Type.VECTOR2:
                factorVec2 = Value.value(factor.getCasted(Vector2Value.class));
                generateTemplateSub(ctx, exprName, lookup, factorVec2, Type._vector2);
                return msValueFactory.createValue(ctx.SUB() != null ? Vector2.neg(factorVec2) : factorVec2, Type._vector2);
            case Type.VECTOR3:
                factorVec3 = Value.value(factor.getCasted(Vector3Value.class));
                generateTemplateSub(ctx, exprName, lookup, factorVec3, Type._vector3);
                return msValueFactory.createValue(ctx.SUB() != null ? Vector3.neg(factorVec3) : factorVec3, Type._vector3);
            default:
                Error("visitNotNegFac");
                return null;
        }
    }

    private void generateTemplateSub(MinespeakParser.NotNegFacContext ctx, String exprName, String lookup, Vector value, Type type) {
        if (lookup == null) {
            if (value instanceof Vector2)
                currentFunc.addTemplate(templateFactory.createAssignST(exprName, (Vector2) value, getPrefix()));
            else if (value instanceof Vector3)
                currentFunc.addTemplate(templateFactory.createAssignST(exprName, (Vector3) value, getPrefix()));
        }

        if (ctx.SUB() != null)
            currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "-", getPrefix(), type));
        else
            currentFunc.addTemplate(templateFactory.createInstanST(exprName, ctx.type, getPrefix()));

        factorNameTable.put(ctx, templateFactory.getExprCounterString());
    }

    private void generateTemplateSub(MinespeakParser.NotNegFacContext ctx, String exprName, String lookup, Integer value, Type type) {
        if (lookup == null)
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, value, getPrefix()));

        if (ctx.SUB() != null)
            currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "-", getPrefix(), type));
        else
            currentFunc.addTemplate(templateFactory.createInstanST(exprName, ctx.type, getPrefix()));

        factorNameTable.put(ctx, templateFactory.getExprCounterString());
    }

    private void generateTemplateSub(MinespeakParser.NotNegFacContext ctx, String exprName, String lookup, Boolean value, Type type) {
        if (lookup == null)
            currentFunc.addTemplate(templateFactory.createAssignST(exprName, value ? 1 : 0, ""));

        if (ctx.NOT() != null)
            currentFunc.addTemplate(templateFactory.createNegationExprST(exprName, "not", getPrefix(), type));
        else
            currentFunc.addTemplate(templateFactory.createInstanST(exprName, ctx.type, getPrefix()));

        factorNameTable.put(ctx, templateFactory.getExprCounterString());
    }

    @Override
    public Value visitPow(MinespeakParser.PowContext ctx) {
        int num1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class));
        int num2 = Value.value(visit(ctx.expr(1)).getCasted(NumValue.class));

        String expr1Name = factorNameTable.get(ctx.expr(0));

        currentFunc.addTemplate(templateFactory.createArithmeticExprST(expr1Name, num2, "Pow", getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return msValueFactory.createValue((int) Math.pow(num1, num2), ctx.type);
    }

    @Override
    public Value visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        Value expr1Visit = visit(ctx.expr(0));
        Value expr2Visit = visit(ctx.expr(1));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));

        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        if (operator == null) {
            Error("visitMulDivMod");
            return null;
        }

        currentFunc.addTemplate(templateFactory.createArithmeticExprST(expr1Name, expr2Name, operator,
                expr1Visit.type, expr2Visit.type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        int num1 = expr1Visit.type == Type._num ? Value.value(expr1Visit.getCasted(NumValue.class)) : 0;
        int num2 = expr2Visit.type == Type._num ? Value.value(expr2Visit.getCasted(NumValue.class)) : 0;
        Vector2 vec21 = expr1Visit.type == Type._vector2 ? Value.value(expr1Visit.getCasted(Vector2Value.class)) : null;
        Vector2 vec22 = expr2Visit.type == Type._vector2 ? Value.value(expr2Visit.getCasted(Vector2Value.class)) : null;
        Vector3 vec31 = expr1Visit.type == Type._vector3 ? Value.value(expr1Visit.getCasted(Vector3Value.class)) : null;
        Vector3 vec32 = expr2Visit.type == Type._vector3 ? Value.value(expr2Visit.getCasted(Vector3Value.class)) : null;

        if (vec21 != null)
            return msValueFactory.createValue(Vector2.scale(vec21, num2) ,Type._vector2);
        else if (vec22 != null)
            return msValueFactory.createValue(Vector2.scale(vec22, num1) ,Type._vector2);
        else if (vec31 != null)
            return msValueFactory.createValue(Vector3.scale(vec31, num2) , Type._vector3);
        else if (vec32 != null)
            return msValueFactory.createValue(Vector3.scale(vec32, num1) , Type._vector3);
        else if (expr1Visit.type == Type._num && expr2Visit.type == Type._num) {
            int val = num1 * num2;
            if (ctx.op.getType() == MinespeakParser.DIV || ctx.op.getType() == MinespeakParser.MOD) {
                if (num2 == 0) {
                    Logger.shared.add(logFactory.createDivideByZeroError(ctx.getText(), ctx.expr(1)));
                    throw new CompileTimeException("Division by zero");
                }
                val = num1 % num2;
                if (ctx.op.getType() == MinespeakParser.DIV)
                    val = num1 / num2;
            }
            return msValueFactory.createValue(val, Type._num);
        }

        Error("InvalidTypes");
        return null;
    }

    @Override
    public Value visitAddSub(MinespeakParser.AddSubContext ctx) {
        Value expr1Visit = visit(ctx.expr(0));
        Value expr2Visit = visit(ctx.expr(1));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));

        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        if (operator == null) {
            Error("visitAddSub:wrongInvalidOperator");
            return null;
        }

        currentFunc.addTemplate(templateFactory.createArithmeticExprST(expr1Name, expr2Name, operator,
                                                                        expr1Visit.type, expr2Visit.type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        // Calculating values
        int num1 = ctx.type == Type._num ? Value.value(expr1Visit.getCasted(NumValue.class)) : 0;
        int num2 = ctx.type == Type._num ? Value.value(expr2Visit.getCasted(NumValue.class)) : 0;
        Vector2 vec21 = ctx.type == Type._vector2 ? Value.value(expr1Visit.getCasted(Vector2Value.class)) : null;
        Vector2 vec22 = ctx.type == Type._vector2 ? Value.value(expr2Visit.getCasted(Vector2Value.class)) : null;
        Vector3 vec31 = ctx.type == Type._vector3 ? Value.value(expr1Visit.getCasted(Vector3Value.class)) : null;
        Vector3 vec32 = ctx.type == Type._vector3 ? Value.value(expr2Visit.getCasted(Vector3Value.class)) : null;

        if (ctx.type == Type._num){
            int val = ctx.op.getType() == MinespeakParser.ADD ? num1 + num2 : num1 - num2;
            return msValueFactory.createValue(val, ctx.type);
        } else if (ctx.type == Type._vector2 && vec21 != null && vec22 != null) {
            Vector2 val = ctx.op.getType() == MinespeakParser.ADD ? Vector2.add(vec21, vec22) : Vector2.sub(vec21, vec22);
            return msValueFactory.createValue(val, ctx.type);
        } else if (ctx.type == Type._vector3 && vec31 != null && vec32 != null) {
            Vector3 val = ctx.op.getType() == MinespeakParser.ADD ? Vector3.add(vec31, vec32) : Vector3.sub(vec31, vec32);
            return msValueFactory.createValue(val, ctx.type);
        }

        Error("visitAddSub:InvalidType");
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
        switch (ctx.type.getTypeAsInt()) {
            case Type.STRING:
            case Type.BLOCK:
                return msValueFactory.createValue(ctx.getText(), ctx.type);
            case Type.NUM:
                return visit(ctx.numberLiteral());
            case Type.BOOL:
                return visit(ctx.booleanLiteral());
            case Type.VECTOR2:
                return visit(ctx.vector2Literal());
            case Type.VECTOR3:
                return visit(ctx.vector3Literal());
            default:
                if (ctx.rArray() != null)
                    return visit(ctx.rArray());

                Error("visitLiteral");
                return null;
        }
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
    public Value visitCompAssign(MinespeakParser.CompAssignContext ctx) {
        return super.visitCompAssign(ctx);
    }

    @Override
    public Value visitRelations(MinespeakParser.RelationsContext ctx) {
        int expr1 = Value.value(visit(ctx.expr(0)).getCasted(NumValue.class));
        int expr2 = Value.value(visit(ctx.expr(1)).getCasted(NumValue.class));
        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));
        boolean exprValue;

        switch (ctx.op.getType()) {
            case MinespeakParser.LESSER:
                exprValue = expr1 < expr2; break;
            case MinespeakParser.GREATER:
                exprValue = expr1 > expr2; break;
            case MinespeakParser.LESSEQ:
                exprValue = expr1 <= expr2; break;
            case MinespeakParser.GREATEQ:
                exprValue = expr1 >= expr2; break;
            default:
                Error("visitRelations");
                return null;
        }

        currentFunc.addTemplate(templateFactory.createRelationExprST(
                expr1Name, expr2Name, SymbolConverter.getSymbol(ctx.op.getType()) , getPrefix()
        ));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return msValueFactory.createValue(exprValue, Type._bool);
    }

    @Override
    public Value visitEquality(MinespeakParser.EqualityContext ctx) {
        Value expr1 = visit(ctx.expr(0));
        Value expr2 = visit(ctx.expr(1));


        SymEntry expr1Lookup = currentScope.lookup(ctx.expr(0).getText());
        SymEntry expr2Lookup = currentScope.lookup(ctx.expr(1).getText());
        String expr1Name = ctx.expr(0).type.equals(Type._block) && expr1Lookup != null
                ? expr1Lookup.getVarName() : factorNameTable.get(ctx.expr(0));
        String expr2Name = ctx.expr(1).type.equals(Type._block) && expr2Lookup != null
                ? expr2Lookup.getVarName() : factorNameTable.get(ctx.expr(1));

        boolean exprValue = (ctx.op.getType() == MinespeakParser.EQUAL) == (expr1.equals(expr2));

        String operator = SymbolConverter.getSymbol(ctx.op.getType());
      
        if (operator == null){
            Error("visitEquality:InvalidOperator");
            return null;
        }

        if (expr1.type == Type._block && expr1Name == null){
            expr1Name = templateFactory.BlockFactor1;
            currentFunc.addTemplate(templateFactory.createAssignST(expr1Name, expr1.getValue().toString(), Type._block, getPrefix()));
        }
      
        if (expr2.type == Type._block && expr2Name == null){
            expr2Name = templateFactory.BlockFactor2;
            currentFunc.addTemplate(templateFactory.createAssignST(expr2Name, expr2.getValue().toString(), Type._block, getPrefix()));
        }

        currentFunc.addTemplate(templateFactory.createEqualityExprST(expr1Name, expr2Name, operator, expr1.type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return msValueFactory.createValue(exprValue, Type._bool);
    }


    @Override
    public Value visitAnd(MinespeakParser.AndContext ctx) {
        Value result = calcLogicalExpr(ctx.expr(0), ctx.expr(1), "and");
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return result;
    }

    @Override
    public Value visitOr(MinespeakParser.OrContext ctx) {
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return calcLogicalExpr(ctx.expr(0), ctx.expr(1), "or");
    }

    @Override
    public Value visitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.LPAREN() != null){
            Value expr = visit(ctx.expr());

            String exprName = factorNameTable.get(ctx.expr());
            currentFunc.addTemplate(templateFactory.createInstanST(exprName, ctx.type, getPrefix()));

            if (ctx.type != Type._block)
                factorNameTable.put(ctx, templateFactory.getExprCounterString());
            return expr;
        } else if (ctx.rvalue() != null){
            factorNameTable.put(ctx, templateFactory.getExprCounterString());
            return visit(ctx.rvalue());
        } else if (ctx.literal() != null) {
            return visit(ctx.literal());
        } else if (ctx.funcCall() != null) {
            return visit(ctx.funcCall());
        } else {
            return visit(ctx.arrayAccess());
        }
    }

    @Override
    public Value visitRvalue(MinespeakParser.RvalueContext ctx) {

        SymEntry lookup = currentScope.lookup(ctx.ID().getText());

        if (lookup == null || lookup.getValue() == null)
            throw new ParameterDependantException();
        else
            factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return lookup.getValue();
    }

    @Override
    public Value visitFunc(MinespeakParser.FuncContext ctx) {
        currentFunc = funcSignature.get(ctx.funcSignature().ID().getText());
        currentFunc.scope = ctx.funcBody().scope;
        currentScope = currentFunc.scope;

        visit(ctx.funcBody());
        currentFunc.isCompiled = true;
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
            } catch (ParameterDependantException e) {   // if stmnt depends on a parameter
                currentFunc.addTemplate(templateFactory.createParamDependantStmntST(child));
            } catch (FuncCompileDependantException e) { // if a function is called before it is compiled
                currentFunc.addTemplate(templateFactory.createFunctionDependantStmntST(child));
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
            SymEntry lookup = currentScope.lookup(ID);
            Type templateType;

            switch (expr.type.getTypeAsInt()) {
                case Type.NUM:
                    lookup.setValue(Value.value(exprEval.getCasted(NumValue.class)));
                    templateType = Type._num; break;
                case Type.BLOCK:
                    lookup.setValue(Value.value(exprEval.getCasted(BlockValue.class)));
                    factorNameTable.put(ctx.initialValue(i).expr(), lookup.getVarName());
                    templateType = Type._block; break;
                case Type.BOOL:
                    lookup.setValue(Value.value(exprEval.getCasted(BoolValue.class)));
                    templateType = Type._bool; break;
                case Type.STRING:
                    lookup.setValue(Value.value(exprEval.getCasted(StringValue.class)));
                    templateType = Type._string; break;
                case Type.VECTOR2:
                    lookup.setValue(Value.value(exprEval.getCasted(Vector2Value.class)));
                    templateType = Type._vector2; break;
                case Type.VECTOR3:
                    lookup.setValue(Value.value(exprEval.getCasted(Vector3Value.class)));
                    templateType = Type._vector3; break;
                default:
                    Error("visitInstan");
                    templateType = Type._error;
            }
            currentFunc.addTemplate(templateFactory.createInstanST(lookup, templateType, getPrefix()));
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
        var func = funcSignature.get(ctx.ID().getText());

        if (!func.isCompiled)
            throw new FuncCompileDependantException();

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

    //region Helper functions
    private Value visitMCStmnt(MinespeakParser.StmntContext ctx) {
        String stmnt = ctx.MCStmnt().getText();
//        currentFunc.addTemplate(templateFactory.createMCStatementST(formatString(stmnt), getPrefix()));
        currentFunc.addTemplate(templateFactory.createMCStatementST(stmnt, getPrefix()));
        return null;
    }

    private String formatString(String stmnt) {
        String command;
        Pattern varReplacePattern = Pattern.compile("(?<prefix>[~^])?v\\{(?<varName>\\w)*}");
        Matcher matcher = varReplacePattern.matcher(stmnt);

        while (matcher.find()) {
            String prefix = matcher.group("prefix");
            prefix = prefix != null ? prefix : "";
            String varName = matcher.group("varName");
            Type type = currentScope.lookup(varName).getType();
            Value varVal = currentScope.lookup(varName).getValue();

            String formatString ="";

            switch (type.getTypeAsInt()) {
                case Type.NUM:
                    formatString = prefix + Value.value(varVal.getCasted(NumValue.class)).toString(); break;
                case Type.BLOCK:
                    formatString = prefix + Value.value(varVal.getCasted(BlockValue.class)); break;
                case Type.BOOL:
                    formatString = prefix + Value.value(varVal.getCasted(BoolValue.class)).toString(); break;
                case Type.STRING:
                    formatString = prefix + Value.value(varVal.getCasted(StringValue.class)); break;
                case Type.VECTOR2:
                    formatString = Value.value(varVal.getCasted(Vector2Value.class)).toString(!prefix.equals("") ? prefix : "~"); break;
                case Type.VECTOR3:
                    formatString = Value.value(varVal.getCasted(Vector3Value.class)).toString(!prefix.equals("") ? prefix : "~"); break;
                default:
                    Error("formatString");
            }

            stmnt = stmnt.replace(prefix + "v{" + varName + "}", formatString);
        }

        command = stmnt.replace("$", "");
        return command;
    }

    private void loadParamsToScope(MinespeakParser.FuncCallContext ctx, FuncEntry func) {
        ArrayList<Template> paramList = new ArrayList<>();

        for (int i = 0; i < func.getParams().size(); i++) {
            String paramName = func.scope.lookup(func.getParams().get(i).getName()).getVarName();
            Value val = visit(ctx.expr(i));

            String exprName = factorNameTable.get(ctx.expr(i));
          
            if (val.Type.equals(Type._block))
                paramList.add(templateFactory.createInstanST(func.scope.lookup(exprName), Type._block, getPrefix()));
            else if (!val.Type.equals(Type._string))
                paramList.add(templateFactory.createInstanST(paramName, exprName, getPrefix()));
          
            SymEntry entry = func.scope.lookup(func.getParams().get(i).getName());

            switch (val.type.getTypeAsInt()) {
                case Type.NUM:
                    entry.setValue(Value.value(val.getCasted(NumValue.class))); break;
                case Type.BLOCK:
                    entry.setValue(Value.value(val.getCasted(BlockValue.class))); break;
                case Type.BOOL:
                    entry.setValue(Value.value(val.getCasted(BoolValue.class))); break;
                case Type.STRING:
                    entry.setValue(Value.value(val.getCasted(StringValue.class))); break;
                case Type.VECTOR2:
                    entry.setValue(Value.value(val.getCasted(Vector2Value.class))); break;
                case Type.VECTOR3:
                    entry.setValue(Value.value(val.getCasted(Vector3Value.class))); break;
                default:
                    Error("loadParamsToScope:InvalidType");
            }
        }
        paramList.addAll(func.getOutput());
        func.setOutput(paramList);
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
            else if (t instanceof FunctionDependantStmntST){
                FunctionDependantStmntST wt = ((FunctionDependantStmntST) t);
                visit(wt.context);  // do stmnt again
                func.getOutput().set(i, func.getOutput().get(outputSize));
                while (outputSize < func.getOutput().size()){
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

        var expr1Name = factorNameTable.get(e1);
        var expr2Name = factorNameTable.get(e2);

        if (expr1Name.equals(templateFactory.factor1UUID))
            output.add(templateFactory.createAssignST(expr1Name, b1 ? 1 : 0, getPrefix()));
        if (expr2Name.equals(templateFactory.factor2UUID))
            output.add(templateFactory.createAssignST(expr2Name, b2 ? 1 : 0, getPrefix()));

        currentFunc.addTemplate(templateFactory.createLogicalExprST(expr1Name, expr2Name, operator, getPrefix()));

        if (operator.equals("and"))
            return msValueFactory.createValue((b1 && b2), Type._bool);
        else if (operator.equals("or"))
            return msValueFactory.createValue((b1 || b2), Type._bool);

        Error("calcLogicalExpr");
        return null;
    }

    private void Error(String errMsg){
        System.out.println(errMsg + ":SHOULD NOT HAPPEN!");
    }
    //endregion
}
