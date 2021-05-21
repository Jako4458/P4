import exceptions.NotImplementedException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class CodeGenVisitor extends MinespeakBaseVisitor<ArrayList<Template>>{
    //region variable instantiations
    private final boolean debug = Main.setup.debug;

    private final boolean useReadableVariableNames = Main.setup.nameMode.equals(NamingMode.readable);
    private boolean setTemplateComments = Main.setup.commenting;
    private final VariableMode variableMode = Main.setup.variableMode;

    private Scope currentScope;
    private final Map<String, FuncEntry> funcSignature;
    private final Map<String, FuncEntry> builtinFunctions;
    private final MSValueFactory msValueFactory = new MSValueFactory();
    private final STemplateFactory templateFactory = new STemplateFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();

    private ArrayList<String> prefixs = new ArrayList<>();
    //endregion

    public CodeGenVisitor(Map<String, FuncEntry> funcSignature, Map<String, FuncEntry> builtinFunctions) {
        this.funcSignature = funcSignature;
        this.builtinFunctions = builtinFunctions;
    }

    //region program header + footer
    private List<Template> makeProgramHeaders() {
        ArrayList<Template> ret = new ArrayList<>();

        BlockValue defaultBlock = msValueFactory.getDefaultBlock();

        ret.add(templateFactory.createMCStatementST(String.format("tag @s add %s", STemplateFactory.getPlayerTag()), ""));
        ret.add(templateFactory.createInstanST(templateFactory.BlockFactor1, defaultBlock, templateFactory.blockFactor1Pos, ""));
        ret.add(templateFactory.createInstanST(templateFactory.BlockFactor2, defaultBlock, templateFactory.blockFactor2Pos, ""));
        return ret;
    }

    private List<Template> makeProgramFooters() {
        ArrayList<Template> ret = new ArrayList<>();

        if (variableMode.equals(VariableMode.delete)){
            ret.add(templateFactory.deleteVariables());
            ret.add(new BlankST("execute as @e[tag=variable] at @e[tag=variable] run setblock ~ ~-1 ~ air", "remove all block variables", setTemplateComments));
            ret.add(new BlankST("kill @e[tag=variable]", "remove variable and expr armor stands", setTemplateComments));
            ret.add(templateFactory.resetExpressions());
        }
        if (!debug)
            ret.add(new BlankST("kill @e[tag=MineSpeak]", "kill all non-variable armor stands", setTemplateComments));

        return ret;
    }
    //endregion

    @Override
    public ArrayList<Template> visitProg(MinespeakParser.ProgContext ctx) {
        enterScope(ctx.scope);
        ArrayList<Template> templates = new ArrayList<>();

        for (FuncEntry func:this.funcSignature.values()) {
            if (!func.isMCFunction()) {
                String generatedName = generateValidFileName();
                func.setName(generatedName);
            }
        }

        for (FuncEntry func:this.funcSignature.values()) {
            templates.addAll(visit(func.getCtx().parent.parent));
        }

        return templates;
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
        ret.addAll(visit(ctx.func()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitFunc(MinespeakParser.FuncContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        // New file here
        if (!(ctx.parent instanceof MinespeakParser.McFuncContext)) {
            String funcName = funcSignature.get(ctx.funcSignature().ID().getText()).getName();
            ret.add(templateFactory.createEnterNewFileST(funcName, false));
        } else {
            ret.add(templateFactory.createEnterNewFileST(ctx.funcSignature().ID().getText().toLowerCase(), true));
            ret.addAll(makeProgramHeaders());
        }

        ret.addAll(visit(ctx.funcSignature()));
        ret.addAll(visit(ctx.funcBody()));
        if (ctx.parent instanceof MinespeakParser.McFuncContext)
            ret.addAll(makeProgramFooters());
        ret.add(templateFactory.createExitFileST());
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        FuncEntry func = funcSignature.get(ctx.ID().getText());
        for (SymEntry param : func.getParams()) {
            SymEntry sl = currentScope.lookup(param.getName());
            ret.add(templateFactory.createInstanST(sl.getVarName(useReadableVariableNames), param.getVarName(useReadableVariableNames), param.getType(), getPrefix()));
        }

        return ret;
    }

    @Override
    public ArrayList<Template> visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        boolean hasRetval = ctx.retVal() != null;
        boolean hasStmnts = ctx.stmnts() != null;

        enterScope(ctx.scope);

        if (hasStmnts)
            ret.addAll(visit(ctx.stmnts()));

        if (hasRetval)
            ret.addAll(visit(ctx.retVal()));

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitRetVal(MinespeakParser.RetValContext ctx) {
        ArrayList<Template> ret;
        FuncEntry func = funcSignature.get(((MinespeakParser.FuncContext)ctx.parent.parent).funcSignature().ID().getText());
        ret = visit(ctx.expr());
        ret.add(templateFactory.createInstanST(func.retVal.getVarName(useReadableVariableNames), factorNameTable.get(ctx.expr()), ctx.expr().type, getPrefix()));
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
            ret.add(templateFactory.createMCStatementST(ctx.MCStmnt().getText().substring(1), getPrefix()));
        else
            ret.addAll(visit(ctx.children.get(0)));
        return ret;
    }

    @Override
    public ArrayList<Template> visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        String whileName = generateValidFileName();

        ret.addAll(generateWhileTemplates(whileName, ctx.body(), ctx.expr()));

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        String whileName = generateValidFileName();

        ret.addAll(visit(ctx.expr()));
        ret.addAll(generateWhileTemplates(whileName, ctx.body(), ctx.expr()));

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        String loopID = generateValidFileName();
        enterScope(ctx.scope);
        ret.addAll(visit(ctx.instan()));
        ret.addAll(visit(ctx.expr()));

        ArrayList<String> oldPrefixs = new ArrayList<>(prefixs);

        ret.add(templateFactory.createFuncCallST(loopID, false, false,
                getPrefix() + "execute unless score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        //new file (forStmntFile)
        prefixs = new ArrayList<>();
        ret.add(templateFactory.createEnterNewFileST(loopID, false));
        ret.addAll(visit(ctx.body()));
        ret.addAll(visit(ctx.assign()));
        ret.addAll(visit(ctx.expr()));
        ret.add(templateFactory.createFuncCallST(loopID, false, false,
                getPrefix() + "execute unless score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        ret.add(templateFactory.createExitFileST());
        //end of file (forStmntFile)

        prefixs = new ArrayList<>(oldPrefixs);

        if (!debug){
            for (TerminalNode ID: ctx.instan().ID()) {
                SymEntry lookup = currentScope.lookup(ID.getText());
                ret.add(new BlankST(getPrefix() + "scoreboard objectives remove " + lookup.getVarName(useReadableVariableNames) + "\n", "", false));
            }
        }

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
                    int valueNum = msValueFactory.getDefaultNum();
                    ret.add(templateFactory.createInstanST(ID, valueNum, getPrefix())); break;
                case Type.BLOCK:
                    BlockValue valueBlock = msValueFactory.getDefaultBlock();
                    ret.add(templateFactory.createInstanST(ID, valueBlock, templateFactory.getNewBlockPos(), ""));
                    break;
                case Type.BOOL:
                    boolean boolValue = msValueFactory.getDefaultBool();
                    ret.add(templateFactory.createInstanST(ID, boolValue ? 1 : 0, getPrefix()));
                    break;
                case Type.VECTOR2:
                case Type.VECTOR3:
                    Vector3Value valueVec3 = msValueFactory.getDefaultVector3();
                    ret.add(templateFactory.createInstanST(ID, valueVec3, getPrefix()));
                    break;
                case Type.STRING:
                    throw new NotImplementedException();
                    //break;
                default:
                    Error("visitDcl");
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

            ret.add(templateFactory.createInstanST(lookup.getVarName(useReadableVariableNames), exprName, expr.type, getPrefix()));
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
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));

        String expr1 = factorNameTable.get(ctx.expr(0));
        String expr2 = factorNameTable.get(ctx.expr(1));

        ret.addAll(templateFactory.createPowTemplates(expr1, expr2, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
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
            String funcRetName = funcSignature.get(ctx.funcCall().ID().getText()).retVal.getVarName(useReadableVariableNames);
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
        factorNameTable.put(ctx, lookup.getVarName(useReadableVariableNames));
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Template> visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        FuncEntry func = funcSignature.get(ctx.ID().getText());
        boolean isBuiltin = false;

        if (func == null) {
            func = this.builtinFunctions.get(ctx.ID().getText());
            isBuiltin = true;
        }

        for (int i = 0; i < ctx.expr().size(); i++) {
            ret.addAll(visit(ctx.expr(i)));
        }

        for (int i = 0; i < ctx.expr().size(); i++) {
            String name = isBuiltin ? func.getParams().get(i).getName() : func.getParams().get(i).getVarName(useReadableVariableNames);
            if (isBuiltin)
                ret.add(templateFactory.createInstanST(name, factorNameTable.get(ctx.expr(i)), ctx.expr(i).type, getPrefix(), func.getName().toLowerCase()));
            else
                ret.add(templateFactory.createInstanST(name, factorNameTable.get(ctx.expr(i)), ctx.expr(i).type, getPrefix()));
        }

        ret.add(templateFactory.createFuncCallST(func.getName().toLowerCase(), func.isMCFunction(), isBuiltin, getPrefix()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitAssign(MinespeakParser.AssignContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        String ID = ctx.ID().getText();
        SymEntry var = currentScope.lookup(ID);
        String varName = var.getVarName(useReadableVariableNames);

        ret.addAll(visit(ctx.expr()));

        Type type = ctx.expr().type;
        String operator = SymbolConverter.getSymbol( ctx.ASSIGN() != null ? MinespeakParser.ASSIGN : ctx.compAssign().op.getType());

        switch (type.getTypeAsInt()) {
            case Type.BOOL:
            case Type.NUM:
            case Type.VECTOR2:
            case Type.VECTOR3:
                if (ctx.ASSIGN() == null)
                    ret.add(templateFactory.createArithmeticExprST(varName, operator, var.getType(), type, getPrefix()));
                ret.add(templateFactory.createAssignST(varName, type, getPrefix()));
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
                BlockValue block = msValueFactory.createBlockValue(ctx.BlockLiteral().getText());
                ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(false), block, templateFactory.getNewBlockPos(), getPrefix()));
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
        ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(false), Integer.parseInt(numText, radix), getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(false), ctx.TRUE() != null ? 1 : 0 , getPrefix()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (var expr: ctx.expr()) {
            ret.addAll(visit(expr));
        }

        String retExpr = templateFactory.getNewExprCounterString(true);

        ret.add(templateFactory.createInstanST(retExpr + "_x", factorNameTable.get(ctx.expr(0)), getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_y", factorNameTable.get(ctx.expr(1)), getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_z", msValueFactory.getDefaultNum(), getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (var expr: ctx.expr()) {
            ret.addAll(visit(expr));
        }

        String retExpr = templateFactory.getNewExprCounterString(true);

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

    private String generateValidFileName() {
        return UUID.randomUUID().toString().toLowerCase();
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

    private ArrayList<Template> generateWhileTemplates(String whileName, MinespeakParser.BodyContext body, MinespeakParser.ExprContext expr) {
        ArrayList<Template> ret = new ArrayList<>();
        ArrayList<String> tempPrefix = new ArrayList<>(prefixs);

        //enter new file
        ret.add(templateFactory.createEnterNewFileST(whileName, false));

        //reset prefix
        prefixs = new ArrayList<>();

        ret.addAll(visit(body));
        ret.addAll(visit(expr));
        ret.add(templateFactory.createFuncCallST(whileName, false, false,
                "execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        //exit file
        ret.add(templateFactory.createExitFileST());

        prefixs = new ArrayList<>(tempPrefix);
        prefixs.add("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
        ret.add(templateFactory.createFuncCallST(whileName, false, false, getPrefix()));

        return ret;
    }

    private void Error(String errMsg){
        throw new RuntimeException(errMsg);
    }
    //endregion
}
