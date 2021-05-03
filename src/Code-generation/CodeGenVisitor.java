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
    private final Map<String, FuncEntry> funcSignature;
    private final MSValueFactory msValueFactory = new MSValueFactory();
    private final STemplateFactory templateFactory = new STemplateFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();
    //TODO: Cleanup
//    private FuncEntry currentFunc;
//    private final LogFactory logFactory = new LogFactory();

    private ArrayList<String> prefixs = new ArrayList<>();
    private ArrayList<Template> output = new ArrayList<>();
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
                templates.addAll(visit(func.getCtx().parent.parent));
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
        this.output = templates;
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
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.func() != null ? ctx.func() : ctx.mcFunc()));
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitMcFunc(MinespeakParser.McFuncContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(makeProgramHeaders());
        ret.addAll(visit(ctx.func()));
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
        ArrayList<Template> ret = new ArrayList<>();
        FuncEntry func = funcSignature.get(ctx.ID().getText());
        for (SymEntry param : func.getParams()) {
            SymEntry sl = currentScope.lookup(param.getName());
            ret.add(templateFactory.createAssignST(sl.getVarName(), param.getVarName(), param.getType(), getPrefix()));
        }

        return ret;
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
        else
            ret.addAll(visit(ctx.children.get(0)));
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

        String whileName = UUID.randomUUID().toString();

        visit(ctx.expr());

        ArrayList<String> tempPrefix = new ArrayList<>(prefixs);

        //enter new file
        ret.add(templateFactory.createEnterNewFileST(whileName, false));

        //reset prefix
        prefixs = new ArrayList<>();

        ret.addAll(visit(ctx.body()));
        ret.addAll(visit(ctx.expr()));
        ret.add(templateFactory.createFuncCallST(whileName, false,
                    "execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        //exit file
        ret.add(templateFactory.createExitFileST());

        prefixs = new ArrayList<>(tempPrefix);
        prefixs.add("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
        ret.add(templateFactory.createFuncCallST(whileName, false, getPrefix()));

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        String loopID = UUID.randomUUID().toString();
        enterScope(ctx.scope);
        visit(ctx.instan());
        visit(ctx.expr());

        ArrayList<String> oldPrefixs = new ArrayList<>(prefixs);

        //TODO: make ForLoop
        ret.add(templateFactory.createFuncCallST(loopID, false,
                getPrefix() + "execute if score @s " + templateFactory.getExprCounterString() + "matches 1 run "));

        //new file (forStmntFile)
        prefixs = new ArrayList<>();
        ret.add(templateFactory.createEnterNewFileST(loopID), false);
        ret.addAll(visit(ctx.body()));
        ret.addAll(visit(ctx.assign()));
        ret.addAll(visit(ctx.expr()));
        ret.add(templateFactory.createFuncCallST(loopID, false,
                getPrefix() + "execute if score @s " + templateFactory.getExprCounterString() + "matches 1 run "));

        ret.add(templateFactory.createExitFileST());
        //end of file (forStmntFile)

        prefixs = new ArrayList<>(oldPrefixs);

//      return super.visitForStmnt(ctx);
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
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);
        if (ctx.stmnts() != null)
            ret = visit(ctx.stmnts());
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitDcls(MinespeakParser.DclsContext ctx) {
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
                case Type.VECTOR2:
                case Type.VECTOR3:
                    Vector3Value valueVec3 = msValueFactory.getDefaultValue(Type._vector3).getCasted(Vector3Value.class);
                    ret.add(templateFactory.createInstanST(ID, valueVec3, getPrefix()));
                    break;
                case Type.STRING:
                    break;  //Don't
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
            String exprName = factorNameTable.get(expr);

            ret.add(templateFactory.createInstanST(lookup.getVarName(), exprName, expr.type, getPrefix()));
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
    public ArrayList<Template> visitPow(MinespeakParser.PowContext ctx) {
        return new ArrayList<>(); //Pow cannot work with the current setup
    }

    @Override
    public ArrayList<Template> visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));

        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        ret.add(templateFactory.createArithmeticExprST(expr1Name, expr2Name, operator,
                ctx.expr(0).type, ctx.expr(1).type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }

    @Override
    public ArrayList<Template> visitAddSub(MinespeakParser.AddSubContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));

        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        ret.add(templateFactory.createArithmeticExprST(expr1Name, expr2Name, operator,
                ctx.expr(0).type, ctx.expr(1).type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }

    @Override
    public ArrayList<Template> visitRelations(MinespeakParser.RelationsContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));

        String expr1Name = factorNameTable.get(ctx.expr(0));
        String expr2Name = factorNameTable.get(ctx.expr(1));

        ret.add(templateFactory.createRelationExprST(
                expr1Name, expr2Name, SymbolConverter.getSymbol(ctx.op.getType()) , getPrefix()
        ));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
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

        ret.add(templateFactory.createEqualityExprST(expr1Name, expr2Name, operator, expr1Type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return ret;
    }

    @Override
    public ArrayList<Template> visitAnd(MinespeakParser.AndContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(calcLogicalExpr(ctx.expr(0), ctx.expr(1), "and"));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }

    @Override
    public ArrayList<Template> visitOr(MinespeakParser.OrContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(calcLogicalExpr(ctx.expr(0), ctx.expr(1), "or"));
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
    public ArrayList<Template> visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        FuncEntry func = funcSignature.get(ctx.ID().getText());

        for (int i = 0; i < ctx.expr().size(); i++) {
            ret.addAll(visit(ctx.expr(i)));
        }

        for (int i = 0; i < ctx.expr().size(); i++) {
            String vName = func.getParams().get(i).getVarName();
            ret.add(templateFactory.createAssignST(func.getParams().get(i).getVarName(), factorNameTable.get(ctx.expr(i)), ctx.expr(i).type, getPrefix()));
        }

        ret.add(templateFactory.createFuncCallST(func.getName(), func.isMCFunction(), getPrefix()));
        return ret;
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

        String exprName = factorNameTable.get(ctx.expr());

        switch (type.getTypeAsInt()) {
            case Type.BOOL:
            case Type.NUM:
            case Type.VECTOR2:
            case Type.VECTOR3:
                if (ctx.ASSIGN() == null)
                    ret.add(templateFactory.createArithmeticExprST(varName, operator, var.getType(), type, getPrefix()));
                ret.add(templateFactory.createAssignST(varName, exprName, type, getPrefix()));
                break;
            case Type.BLOCK:
                ret.add(templateFactory.createAssignST(varName, type, getPrefix()));
                break;
            case Type.STRING:
            default:
                Error("visitAssign");
        }
        return ret;
    }

    //region literal
    @Override
    public ArrayList<Template> visitLiteral(MinespeakParser.LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        switch (ctx.type.getTypeAsInt()) {
            case Type.BLOCK:
                BlockValue block = (BlockValue)msValueFactory.createValue(ctx.BlockLiteral().getText(), Type._block);
                ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(), block, templateFactory.getNewBlockPos(), getPrefix()));
                break;
            case Type.NUM:
                ret.addAll(visit(ctx.numberLiteral())); break;
            case Type.BOOL:
                ret.addAll(visit(ctx.booleanLiteral())); break;
            case Type.VECTOR2:
                ret.addAll(visit(ctx.vector2Literal())); break;
            case Type.VECTOR3:
                ret.addAll(visit(ctx.vector3Literal())); break;
            case Type.STRING:
            default:
                Error("VisitLiteral: Invalid type");
        }
        return ret;
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
