import jdk.dynalink.linker.support.Lookup;
import logging.Logger;
import exceptions.CompileTimeException;
import exceptions.FuncCompileDependantException;
import exceptions.ParameterDependantException;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGenVisitor extends MinespeakBaseVisitor<ArrayList<Template>>{
    //region variable instantiations
    private final boolean debug = true;
    private Scope currentScope;
    private FuncEntry currentFunc;
    private final Map<String, FuncEntry> funcSignature;
    private final MSValueFactory msValueFactory = new MSValueFactory();
    private final STemplateFactory templateFactory = new STemplateFactory();
    private final LogFactory logFactory = new LogFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();

    private final ArrayList<String> prefixs = new ArrayList<>();
    private final ArrayList<Template> output = new ArrayList<>();
    //endregion

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature) {
        this.funcSignature = funcSignature;
    }

    //region program header + footer
    private List<Template> makeProgramHeaders() {
        ArrayList<Template> ret = new ArrayList<>();

        if (debug)
            ret.add(templateFactory.createMCStatementST("scoreboard players reset @s", "")); // for debug

        int defaultNum = Value.value(msValueFactory.getDefaultValue(Type._num).getCasted(NumValue.class));
        Vector3Value defaultVector3 = msValueFactory.getDefaultValue(Type._vector3).getCasted(Vector3Value.class);
        BlockValue defaultBlock = msValueFactory.getDefaultValue(Type._block).getCasted(BlockValue.class);

        ret.add(templateFactory.createInstanST(templateFactory.factor1UUID, defaultNum, ""));
        ret.add(templateFactory.createInstanST(templateFactory.factor2UUID, defaultNum, ""));
        ret.add(templateFactory.createInstanST(templateFactory.factor1UUID, defaultVector3, ""));
        ret.add(templateFactory.createInstanST(templateFactory.factor2UUID, defaultVector3, ""));
        ret.add(templateFactory.createInstanST("BlockFactor1", defaultBlock, templateFactory.blockFactor1Pos, ""));
        ret.add(templateFactory.createInstanST("BlockFactor2", defaultBlock, templateFactory.blockFactor2Pos, ""));
        return ret;
    }

    private List<Template> makeProgramFooters() {
        ArrayList<Template> ret = new ArrayList<>();
        if (debug)
            return ret;

        ret.add(new BlankST("execute as @e[tag=variable] at @e[tag=variable] run setblock ~ ~-1 ~ air"));
        ret.add(new BlankST("kill @e[tag=MineSpeak]"));
        return ret;
    }
    //endregion

    @Override
    public ArrayList<Template> visitProg(MinespeakParser.ProgContext ctx) {
        enterScope(ctx.scope);
        boolean debug = true;
        ArrayList<Template> templates = new ArrayList<>();

           // set to false when not debugging

        for (FuncEntry func:this.funcSignature.values()) {
            try {
                templates.addAll(visit(func.getCtx().parent));
            } catch (CompileTimeException e) {
                return null;
            }
        }
/*
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
*/

        printOutput();
        return null;
    }

    @Override
    public ArrayList<Template> visitBlocks(MinespeakParser.BlocksContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (MinespeakParser.BlockContext child : ctx.block()) {
            ret.addAll(visit(child));
        }

        return ret;
    }

    @Override
    public ArrayList<Template> visitBlock(MinespeakParser.BlockContext ctx) {
        enterScope(ctx.scope);
        ArrayList<Template> ret = super.visitBlock(ctx);
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitMcFunc(MinespeakParser.McFuncContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(makeProgramHeaders());
        ret.addAll(super.visitMcFunc(ctx));
        ret.addAll(makeProgramFooters());

        return ret;
    }

    @Override
    public ArrayList<Template> visitFunc(MinespeakParser.FuncContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        // New file here
        if (ctx.parent instanceof MinespeakParser.McFuncContext) {
            // Make the file with set name and in correct folder
        } else {
            // Make the file with random name in bin folder
        }

        ret.addAll(visit(ctx.funcSignature()));
        ret.addAll(visit(ctx.funcBody()));
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        FuncEntry func = funcSignature.get(ctx.ID().getText());

        for (SymEntry entry : func.getParams()) {
            entry.setName(STemplateFactory.generateValidUUID());    //TODO:check
        }

        return new ArrayList<>();
    }

    @Override
    public ArrayList<Template> visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        boolean hasRetval = ctx.retVal() != null;

        enterScope(ctx.scope);

        if (ctx.stmnts() != null)
            ret.addAll(visit(ctx.stmnts()));

        if (hasRetval)
            ret.addAll(visit(ctx.retVal()));

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitRetVal(MinespeakParser.RetValContext ctx) {
        ArrayList<Template> ret;
        ret = visit(ctx.expr());
        ret.add(templateFactory.createAssignST(currentScope.lookup("return").getVarName(), ctx.expr().type, getPrefix()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitStmnts(MinespeakParser.StmntsContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (MinespeakParser.StmntContext child : ctx.stmnt()) {
            ret.addAll(visit(child));
        }

        return ret;
    }

    @Override
    public ArrayList<Template> visitStmnt(MinespeakParser.StmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        if (ctx.MCStmnt() != null)
            ret.add(templateFactory.createMCStatementST(ctx.MCStmnt().getText(), getPrefix()));

        ret.addAll(super.visit(ctx));   //TODO: check
        return ret;
    }

    @Override
    public ArrayList<Template> visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        ret.add(null);//TODO: Make dowhileST

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        ret.add(null);//TODO: Make whileST

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);
        visit(ctx.instan());
        //TODO: make ForLoop
        //new file (forStmntFile)



        //end of file (forStmntFile)

//        return super.visitForStmnt(ctx);
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        prefixs.add("");
        for (int i = 0; i < ctx.expr().size(); i++) {
            String currentPrefix = prefixs.get(prefixs.size() - 1);
            currentPrefix = currentPrefix.replace("matches 1", "matches 0");
            prefixs.set(prefixs.size() - 1, currentPrefix);

            ret.addAll(visit(ctx.expr(i)));
            currentPrefix = currentPrefix.concat("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
            prefixs.set(prefixs.size() - 1, currentPrefix);
            ret.addAll(visit(ctx.body(i)));
        }

        if (ctx.ELSE() != null){
            prefixs.set(prefixs.size() - 1, prefixs.get(prefixs.size() - 1).replace("matches 1", "matches 0"));
            ret.addAll(visit(ctx.body(ctx.body().size() - 1)));
        }

        prefixs.remove(prefixs.size() - 1);
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitBody(MinespeakParser.BodyContext ctx) {
        ArrayList<Template> ret;
        enterScope(ctx.scope);
        ret = super.visit(ctx);
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitDcls(MinespeakParser.DclsContext ctx) { //FIX?FIX!
        ArrayList<Template> ret = new ArrayList<>();

        for (int i = 0; i < ctx.ID().size(); i++){
            String ID = ctx.ID(i).getText();

            switch (ctx.primaryType(i).type.getTypeAsInt()) {
                case Type.NUM:
                    int valueNum = Value.value(msValueFactory.getDefaultValue(Type._num).getCasted(NumValue.class));
                    ret.add(templateFactory.createInstanST(ID, valueNum, getPrefix())); break;
                case Type.BLOCK:
                    BlockValue valueBlock = msValueFactory.getDefaultValue(Type._block).getCasted(BlockValue.class);
                    ret.add(templateFactory.createInstanST(ID, valueBlock, templateFactory.getNewBlockPos(), ""));
                    break;
                case Type.BOOL:
                    boolean boolValue = Value.value(msValueFactory.getDefaultValue(Type._bool).getCasted(BoolValue.class));
                    ret.add(templateFactory.createInstanST(ID, boolValue ? 1 : 0, getPrefix()));
                    break;
                case Type.STRING:
                    break;  //TODO: Don't
                case Type.VECTOR2:
                case Type.VECTOR3:
                    Vector3Value valueVec3 = msValueFactory.getDefaultValue(Type._vector3).getCasted(Vector3Value.class);
                    ret.add(templateFactory.createInstanST(ID, valueVec3, getPrefix()));
                    break;
                default:
                    Error("visitInstan");
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Template> visitInstan(MinespeakParser.InstanContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (int i = 0; i < ctx.ID().size(); i++) {
            String ID = ctx.ID(i).getText();

            var expr = ctx.initialValue(i).expr();
            ret.addAll(visit(expr));
            SymEntry lookup = currentScope.lookup(ID);

            currentFunc.addTemplate(templateFactory.createInstanST(lookup.getVarName(), expr.type, getPrefix()));
        }
        return ret;
    }

    //region expr
    @Override
    public ArrayList<Template> visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.factor()));
        String exprName = factorNameTable.get(ctx.factor());

        if (ctx.SUB() != null) {
            ret.add(templateFactory.createNegationExprST(exprName, "-", getPrefix(), ctx.type));
            exprName = templateFactory.getExprCounterString();
        } else if (ctx.NOT() != null) {
            ret.add(templateFactory.createNegationExprST(exprName, "not", getPrefix(), ctx.type));
            exprName = templateFactory.getExprCounterString();
        }

        factorNameTable.put(ctx, exprName);
        return ret;
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
    public ArrayList<Template> visitEquality(MinespeakParser.EqualityContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));
        Type expr1Type = ctx.expr(0).type;
        Type expr2Type = ctx.expr(1).type;

        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        if (operator == null)
            Error("visitEquality:InvalidOperator");

        if (expr1Type == Type._block && expr1Name == null){
            expr1Name = templateFactory.BlockFactor1;
            //TODO:BLOCKS
//            currentFunc.addTemplate(templateFactory.createAssignST(expr1Name, expr1.getValue().toString(), Type._block, getPrefix()));
        }

        if (expr2Type == Type._block && expr2Name == null){
            //TODO:BLOCKS
            expr2Name = templateFactory.BlockFactor2;
//            currentFunc.addTemplate(templateFactory.createAssignST(expr2Name, expr2.getValue().toString(), Type._block, getPrefix()));
        }

        currentFunc.addTemplate(templateFactory.createEqualityExprST(expr1Name, expr2Name, operator, expr1Type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return msValueFactory.createValue(exprValue, Type._bool);
    }

    @Override
    public ArrayList<Template> visitAnd(MinespeakParser.AndContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret = calcLogicalExpr(ctx.expr(0), ctx.expr(1), "and");
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }

    @Override
    public ArrayList<Template> visitOr(MinespeakParser.OrContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret = calcLogicalExpr(ctx.expr(0), ctx.expr(1), "or");
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }
    //endregion

    @Override
    public ArrayList<Template> visitFactor(MinespeakParser.FactorContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        if (ctx.LPAREN() != null) {
            ret.addAll(visit(ctx.expr()));
            factorNameTable.put(ctx, factorNameTable.get(ctx.expr()));
            return ret;
        } else if (ctx.rvalue() != null) {
            ret.addAll(visit(ctx.rvalue()));
            factorNameTable.put(ctx, factorNameTable.get(ctx.rvalue()));
            return ret;
        } else if (ctx.literal() != null) {
            ret.addAll(visit(ctx.literal()));
            factorNameTable.put(ctx, templateFactory.getExprCounterString());
            return ret;
        } else if (ctx.funcCall() != null) {
            ret.addAll(visit(ctx.funcCall()));
            String funcRetName = funcSignature.get(ctx.funcCall().ID().getText()).scope.lookup("return").getVarName();
            factorNameTable.put(ctx, funcRetName);
            return ret;
        } else if (ctx.rArray() != null) {
            return visit(ctx.rArray());
        } else {
            return visit(ctx.arrayAccess());
        }
    }

    @Override
    public ArrayList<Template> visitRvalue(MinespeakParser.RvalueContext ctx) {
        SymEntry lookup = currentScope.lookup(ctx.ID().getText());
        factorNameTable.put(ctx, lookup.getVarName());
        return new ArrayList<Template>();
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

    @Override
    public ArrayList<Template> visitAssign(MinespeakParser.AssignContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        String ID = ctx.ID().getText();
        SymEntry var = currentScope.lookup(ID);
        String varName = var.getVarName();

        ret.addAll(visit(ctx.expr()));

        Type type = ctx.expr().type;

        String operator = SymbolConverter.getSymbol( ctx.ASSIGN() != null ? MinespeakParser.ASSIGN : ctx.compAssign().op.getType());

        switch (type.getTypeAsInt()) {
            case Type.BLOCK:
                if (ctx.ASSIGN() != null)
                    //TODO: FIX BLOCK
                    currentFunc.addTemplate(templateFactory.createAssignST(varName, Value.value(exprVal.getCasted(BlockValue.class)), Type._block, getPrefix()));
                break;
            case Type.NUM:
            case Type.VECTOR2:
            case Type.VECTOR3:
                if (ctx.ASSIGN() == null)
                    ret.add(templateFactory.createArithmeticExprST(varName, operator, var.getType(), type, getPrefix()));
                ret.add(templateFactory.createAssignST(varName, type, getPrefix()));
                break;
            case Type.STRING:
            case Type.BOOL:
            default:
                Error("visitAssign");
                return null;
        }
        return  ret;
    }

    //region literal
    @Override
    public ArrayList<Template> visitLiteral(MinespeakParser.LiteralContext ctx) {
        switch (ctx.type.getTypeAsInt()) {
            case Type.BLOCK:
                //TODO FIX
                BlockValue block = msValueFactory.createValue(ctx.BlockLiteral().getText(), Type._block);
                templateFactory.createInstanST(templateFactory.getNewExprCounterString(), , );
            case Type.NUM:
                return visit(ctx.numberLiteral());
            case Type.BOOL:
                return visit(ctx.booleanLiteral());
            case Type.VECTOR2:
                return visit(ctx.vector2Literal());
            case Type.VECTOR3:
                return visit(ctx.vector3Literal());
            case Type.STRING:
            default:
                Error("VisitLiteral: Invalid type");
        }
    }

    @Override
    public ArrayList<Template> visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        int radix = ctx.DecimalDigit() != null ? 10 : 16;
        String numText = radix == 10 ? ctx.getText(): ctx.getText().substring(2); // cut '0x' from hex
        ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(), Integer.parseInt(numText, radix), getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(), ctx.TRUE() != null ? 1 : 0 , getPrefix()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (var expr: ctx.expr()) {
            ret.addAll(visit(expr));
        }

        String retExpr = templateFactory.getNewExprCounterString();

        ret.add(templateFactory.createInstanST(retExpr + "_x", factorNameTable.get(ctx.expr(0)), getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_y", factorNameTable.get(ctx.expr(1)), getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (var expr: ctx.expr()) {
            ret.addAll(visit(expr));
        }

        String retExpr = templateFactory.getNewExprCounterString();

        ret.add(templateFactory.createInstanST(retExpr + "_x", factorNameTable.get(ctx.expr(0)), getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_y", factorNameTable.get(ctx.expr(1)), getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_z", factorNameTable.get(ctx.expr(2)), getPrefix()));

        return ret;
    }
    //endregion

    //region Helper functions
    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private String getPrefix() {
        StringBuilder builder = new StringBuilder();
        for (String string : prefixs) {
            builder.append(string);
        }
        return builder.toString();
    }

    private void printOutput() {
        for (Template t:output) {
            System.out.println(t.getOutput());
        }
    }
    
    private ArrayList<Template> calcLogicalExpr(MinespeakParser.ExprContext e1, MinespeakParser.ExprContext e2, String operator) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(e1));
        ret.addAll(visit(e2));

        String expr1Name = factorNameTable.get(e1);
        String expr2Name = factorNameTable.get(e2);

        ret.add(templateFactory.createLogicalExprST(expr1Name, expr2Name, operator, getPrefix()));

        return ret;
    }

    private void Error(String errMsg){
        throw new RuntimeException(errMsg);
    }
    //endregion
}
