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

    private static LogFactory logFactory = new LogFactory();
    private Scope currentScope;
    private final Map<String, FuncEntry> funcSignature;
    private final Map<String, FuncEntry> builtinFunctions;
    private final MSValueFactory msValueFactory = new MSValueFactory();
    private final STemplateFactory templateFactory = new STemplateFactory();
    private final Map<ParseTree, String> factorNameTable = new HashMap<>();

    private ArrayList<String> prefixs = new ArrayList<>();
    //endregion

    /**
     * Setup of the CodeGenVisitor
     * @param funcSignature hashMap with function signatures as <String, FuncEntry>
     * @param builtinFunctions hashMap with builtin function as <String, FuncEntry>
     */
    public CodeGenVisitor(Map<String, FuncEntry> funcSignature, Map<String, FuncEntry> builtinFunctions) {
        this.funcSignature = funcSignature;
        this.builtinFunctions = builtinFunctions;
    }

    //region program header + footer

    /**
     * Generates headers for @mc functions
     * Adds active tag to player
     * @return Ordered list of templates to be used as header in @mc functions
     */
    private List<Template> makeProgramHeaders() {
        ArrayList<Template> ret = new ArrayList<>();

        ret.add(templateFactory.createMCStatementST(String.format("tag @s add %s", STemplateFactory.getPlayerTag()), ""));
        return ret;
    }

    /**
     * Generates footers for @mc functions
     * Removes variables and expressions based on debug and variableMode
     * @return Ordered list of templates to be used as footer in @mc functions
     */
    private List<Template> makeProgramFooters() {
        ArrayList<Template> ret = new ArrayList<>();

        if (variableMode.equals(VariableMode.delete)){
            ret.add(templateFactory.resetVariables());
            ret.add(new BlankST("execute as @e[tag=variable] at @e[tag=variable] run setblock ~ ~-1 ~ air", "remove all block variables", setTemplateComments));
            ret.add(new BlankST("setblock 0 255 0 air", "remove blockfactor1", false));
            ret.add(new BlankST("setblock 1 255 0 air", "remove blockfactor2", false));
            ret.add(new BlankST("kill @e[tag=variable]", "remove variable and expr armor stands", setTemplateComments));
            ret.add(templateFactory.resetExpressions());
        }
        if (!debug)
            ret.add(new BlankST("kill @e[tag=MineSpeak]", "kill all non-variable armor stands", setTemplateComments));

        return ret;
    }
    //endregion

    /**
     * Walks through a ParseTree of a Minespeak program
     * @param ctx ParseTree Node of type ProgContext to visit
     * @return List of templates resulting from compilation of a Minespeak (.ms) file
     */
    @Override
    public ArrayList<Template> visitProg(MinespeakParser.ProgContext ctx) {
        enterScope(ctx.scope);
        ArrayList<Template> templates = new ArrayList<>();

        ArrayList<FuncEntry> nonMcFuncs = new ArrayList<>();
        ArrayList<FuncEntry> mcFuncs = new ArrayList<>();

        // generates names for the non-mc functions
        for (FuncEntry func:this.funcSignature.values()) {
            if (!func.isMCFunction()) {
                String generatedName = generateValidFileName();
                func.setName(generatedName);
                nonMcFuncs.add(func);
            }else {
                mcFuncs.add(func);
            }
        }

        // visit all functions
        for (FuncEntry func:nonMcFuncs)
            templates.addAll(visit(func.getCtx().parent.parent));

        for (FuncEntry func:mcFuncs)
            templates.addAll(visit(func.getCtx().parent.parent));

        return templates;
    }


    @Override
    public ArrayList<Template> visitBlocks(MinespeakParser.BlocksContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // visit all blocks
        for (MinespeakParser.BlockContext child : ctx.block()) {
            ret.addAll(visit(child));
        }

        return ret;
    }

    @Override
    public ArrayList<Template> visitBlock(MinespeakParser.BlockContext ctx) {
        enterScope(ctx.scope);
        ArrayList<Template> ret = new ArrayList<>();

        // visit underlying func or mc-func
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
        FuncEntry func = funcSignature.get(ctx.funcSignature().ID().getText());
        String funcName = func.getName();
        enterScope(ctx.scope);

        // create new file with correct name
        if (!(ctx.parent instanceof MinespeakParser.McFuncContext))
            ret.add(templateFactory.createEnterNewFileST(funcName, false));
        else {
            ret.add(templateFactory.createEnterNewFileST(ctx.funcSignature().ID().getText().toLowerCase(), true));
            // if function is mc -> insert headers
            ret.addAll(makeProgramHeaders());
        }

        ret.addAll(visit(ctx.funcSignature()));
        ret.addAll(visit(ctx.funcBody()));

        // if debug -> delete expressions
        func.cleanupTemplate = debug ? templateFactory.resetExpressions() : new BlankST("", "", false);

        // if variableMode is delete -> delete variables
        String cleanupString = variableMode.equals(VariableMode.delete) ? func.cleanupTemplate.getOutput() + templateFactory.resetVariables().getOutput() : func.cleanupTemplate.getOutput();
        func.cleanupTemplate = new BlankST(cleanupString,"Garbage collection: " + funcName, true);

        // if function is mc -> cleanup and insert footers
        if (func.isMCFunction()){

            // do cleanup for all non-mcfunctions
            for (FuncEntry funcToCleanup: funcSignature.values()){
                if (!funcToCleanup.isMCFunction())
                    ret.add(funcToCleanup.cleanupTemplate);
            }
            // insert footer
            ret.add(func.cleanupTemplate);
            ret.addAll(makeProgramFooters());
        }

        ret.add(templateFactory.createExitFileST());
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // get function from ID
        FuncEntry func = funcSignature.get(ctx.ID().getText());

        // instantiate scope with values in parameters
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

        // find function from id
        FuncEntry func = funcSignature.get(((MinespeakParser.FuncContext)ctx.parent.parent).funcSignature().ID().getText());

        ret = visit(ctx.expr());

        // instantiate the return value
        ret.add(templateFactory.createInstanST(func.retVal.getVarName(useReadableVariableNames), factorNameTable.get(ctx.expr()), ctx.expr().type, getPrefix()));
        return ret;
    }

    @Override
    public ArrayList<Template> visitStmnts(MinespeakParser.StmntsContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // visit all smtnts
        for (MinespeakParser.StmntContext child : ctx.stmnt()) {
            ret.addAll(visit(child));
        }

        return ret;
    }

    @Override
    public ArrayList<Template> visitStmnt(MinespeakParser.StmntContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        if (ctx.MCStmnt() != null)
            // create MCStatement and cut '$' from the command
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

        // if condition is false (before the until) enter the loop
        ret.add(templateFactory.createFuncCallST(loopID, false, false,
                getPrefix() + "execute unless score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        //new file (forStmntFile)
        prefixs = new ArrayList<>();
        ret.add(templateFactory.createEnterNewFileST(loopID, false));
        ret.addAll(visit(ctx.body()));
        ret.addAll(visit(ctx.assign()));
        ret.addAll(visit(ctx.expr()));

        // if condition is still false recursively call loop
        ret.add(templateFactory.createFuncCallST(loopID, false, false,
                getPrefix() + "execute unless score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        ret.add(templateFactory.createExitFileST());
        //end of file (forStmntFile)

        prefixs = new ArrayList<>(oldPrefixs);


        if (!debug){
            // if not debug delete iterator
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

        // for expressions change prefix
        prefixs.add(""); // empty string to separate if-stmnts
        for (int i = 0; i < ctx.expr().size(); i++) {
            // set previous expr to false (0)
            String currentPrefix = prefixs.get(prefixs.size() - 1);
            currentPrefix = currentPrefix.replace("matches 1", "matches 0");
            prefixs.set(prefixs.size() - 1, currentPrefix);

            // set current to true (1)
            ret.addAll(visit(ctx.expr(i)));
            currentPrefix = currentPrefix.concat("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
            prefixs.set(prefixs.size() - 1, currentPrefix);

            // visit body with prefix
            ret.addAll(visit(ctx.body(i)));
        }

        if (ctx.ELSE() != null){
            // set all to false (0)
            prefixs.set(prefixs.size() - 1, prefixs.get(prefixs.size() - 1).replace("matches 1", "matches 0"));
            ret.addAll(visit(ctx.body(ctx.body().size() - 1)));
        }

        // remove if-stmnt
        prefixs.remove(prefixs.size() - 1);
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitBody(MinespeakParser.BodyContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();
        enterScope(ctx.scope);

        // visit all stmnts in body
        if (ctx.stmnts() != null)
            ret = visit(ctx.stmnts());

        exitScope();
        return ret;
    }

    @Override
    public ArrayList<Template> visitDcls(MinespeakParser.DclsContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // declare all variables in Dcl as default value
        // vector2 and vector3 as same case
        for (int i = 0; i < ctx.ID().size(); i++){
            String ID = ctx.ID(i).getText();

            switch (ctx.primaryType(i).type.getTypeAsInt()) {
                case Type.NUM:
                    int valueNum = msValueFactory.getDefaultNum();
                    ret.add(templateFactory.createInstanST(ID, valueNum, getPrefix()));
                    break;
                case Type.BLOCK:
                    BlockValue valueBlock = msValueFactory.getDefaultBlock();
                    ret.add(templateFactory.createInstanST(ID, valueBlock, ""));
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
                    throw new NotImplementedException("The string type is not implemented.", ctx);
                default:
                    Error("visitDcl");
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Template> visitInstan(MinespeakParser.InstanContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // instantiate all Instans
        for (int i = 0; i < ctx.ID().size(); i++) {
            String ID = ctx.ID(i).getText();

            MinespeakParser.ExprContext expr = ctx.initialValue(i).expr();
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
            // negative expression
            ret.add(templateFactory.createNegationExprST(exprName, "-", getPrefix(), ctx.type));
            exprName = templateFactory.getExprCounterString();
        } else if (ctx.NOT() != null) {
            // logical not operation
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

        // get operator (* or / or %)
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

        // get operator (+ or -)
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

        // get symbol (== or !=)
        String operator = SymbolConverter.getSymbol(ctx.op.getType());

        ret.add(templateFactory.createEqualityExprST(expr1Name, expr2Name, operator, expr1Type, getPrefix()));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());

        return ret;
    }

    @Override
    public ArrayList<Template> visitAnd(MinespeakParser.AndContext ctx) {
        // calculate expression and add it into FactorNameTable
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(calcLogicalExpr(ctx.expr(0), ctx.expr(1), "and"));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }

    @Override
    public ArrayList<Template> visitOr(MinespeakParser.OrContext ctx) {
        // calculate expression and add it into FactorNameTable
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(calcLogicalExpr(ctx.expr(0), ctx.expr(1), "or"));
        factorNameTable.put(ctx, templateFactory.getExprCounterString());
        return ret;
    }
    //endregion

    @Override
    public ArrayList<Template> visitFactor(MinespeakParser.FactorContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // visit the factor and put it with name into the factorNameTable
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
            factorNameTable.put(ctx, factorNameTable.get(ctx.funcCall()));
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

        // if not in func signature -> check builtin
        if (func == null) {
            func = this.builtinFunctions.get(ctx.ID().getText());
            isBuiltin = true;
        }

        // visit parameters
        for (int i = 0; i < ctx.expr().size(); i++) {
            ret.addAll(visit(ctx.expr(i)));
        }

        // instan parameters
        for (int i = 0; i < ctx.expr().size(); i++) {
            String name = isBuiltin ? func.getParams().get(i).getName() : func.getParams().get(i).getVarName(useReadableVariableNames);
            if (isBuiltin)
                ret.add(templateFactory.createInstanST(name, factorNameTable.get(ctx.expr(i)), ctx.expr(i).type, getPrefix(), func.getName().toLowerCase()));
            else
                ret.add(templateFactory.createInstanST(name, factorNameTable.get(ctx.expr(i)), ctx.expr(i).type, getPrefix()));
        }

        // call func
        ret.add(templateFactory.createFuncCallST(func.getName().toLowerCase(), func.isMCFunction(), isBuiltin, getPrefix()));

        // if not void -> instan return value
        if (!func.getType().equals(Type._void)){
            Type retType = func.retVal.getType();
            String retName = func.retVal.getVarName(useReadableVariableNames);
            ret.add(templateFactory.createInstanST(
                    templateFactory.getNewExprCounterString(
                            retType.getTypeAsInt() == Type.VECTOR3 || retType.getTypeAsInt() == Type.VECTOR2
                    ),
                    retName,
                    retType,
                    getPrefix())
            );
            factorNameTable.put(ctx, templateFactory.getExprCounterString());
        }

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
        // get operator as either assign or compound assign
        String operator = SymbolConverter.getSymbol( ctx.ASSIGN() != null ? MinespeakParser.ASSIGN : ctx.compAssign().op.getType());

        switch (type.getTypeAsInt()) {
            // same case for bool, num, vector2, and vector3
            case Type.BOOL:
            case Type.NUM:
            case Type.VECTOR2:
            case Type.VECTOR3:
                // if assign is compound  -> evaluate new value
                if (ctx.ASSIGN() == null)
                    ret.add(templateFactory.createArithmeticExprST(varName, operator, var.getType(), type, getPrefix()));
                ret.add(templateFactory.createAssignST(varName, type, getPrefix()));
                break;
            case Type.BLOCK:
                ret.add(templateFactory.createAssignST(varName, type, getPrefix()));
                break;
            case Type.STRING:
                throw new NotImplementedException("The string type is not implemented.", ctx);
            default:
                Error("visitAssign");
        }
        return ret;
    }

    //region literal
    @Override
    public ArrayList<Template> visitLiteral(MinespeakParser.LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // visit literal
        switch (ctx.type.getTypeAsInt()) {
            case Type.BLOCK:
                BlockValue block = msValueFactory.createBlockValue(ctx.BlockLiteral().getText());
                ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(false), block, getPrefix()));
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
                throw new NotImplementedException("The string type is not implemented.", ctx);
            default:
                Error("VisitLiteral: Invalid type");
        }
        return ret;
    }

    @Override
    public ArrayList<Template> visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // get the base (10 or 16)
        int radix = ctx.DecimalDigit() != null ? 10 : 16;

        // if hexadecial (base 16) cut '0x'
        String numText = radix == 10 ? ctx.getText(): ctx.getText().substring(2);

        // parse to int
        ret.add(templateFactory.createInstanST(templateFactory.getNewExprCounterString(false), Integer.parseInt(numText, radix), getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        // instantiate boolean as num (1 or 0)
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

        // instantiate vectors seperate values
        ret.add(templateFactory.createInstanST(retExpr + "_x", factorNameTable.get(ctx.expr(0)), Type._num,getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_y", factorNameTable.get(ctx.expr(1)), Type._num, getPrefix()));

        return ret;
    }

    @Override
    public ArrayList<Template> visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        ArrayList<Template> ret = new ArrayList<>();

        for (var expr: ctx.expr()) {
            ret.addAll(visit(expr));
        }

        String retExpr = templateFactory.getNewExprCounterString(true);

        // instantiate vectors seperate values
        ret.add(templateFactory.createInstanST(retExpr + "_x", factorNameTable.get(ctx.expr(0)), Type._num, getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_y", factorNameTable.get(ctx.expr(1)), Type._num, getPrefix()));
        ret.add(templateFactory.createInstanST(retExpr + "_z", factorNameTable.get(ctx.expr(2)), Type._num, getPrefix()));

        return ret;
    }
    //endregion

    //region Helper functions

    /**
     * Enter a new scope
     * @param scope Which scope to enter
     */
    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    /**
     * Exit current scope
     */
    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    /**
     * Get the prefix to be used in MCFunction
     * @return String prefix
     */
    private String getPrefix() {
        StringBuilder builder = new StringBuilder();

        // build string consisting of all prefixes in prefixs
        for (String string : prefixs) {
            builder.append(string);
        }
        return builder.toString();
    }

    /**
     * Generate a valid name for a .mcfunction file
     * @return Generated name as a String
     */
    private String generateValidFileName() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    /**
     * Calculate logical expression with different operators
     * @param e1 Right operand
     * @param e2 Left operand
     * @param operator Operator to be used
     * @return Template to calculate logical expression
     */
    private ArrayList<Template> calcLogicalExpr(MinespeakParser.ExprContext e1, MinespeakParser.ExprContext e2, String operator) {
        ArrayList<Template> ret = new ArrayList<>();
        ret.addAll(visit(e1));
        ret.addAll(visit(e2));

        String expr1Name = factorNameTable.get(e1);
        String expr2Name = factorNameTable.get(e2);

        ret.add(templateFactory.createLogicalExprST(expr1Name, expr2Name, operator, getPrefix()));

        return ret;
    }

    /**
     * Generate template for while structure
     * @param whileName Name for while (Used to in a new file)
     * @param body Body of the while
     * @param expr Condition of while
     * @return Template for the generated while
     */
    private ArrayList<Template> generateWhileTemplates(String whileName, MinespeakParser.BodyContext body, MinespeakParser.ExprContext expr) {
        ArrayList<Template> ret = new ArrayList<>();
        ArrayList<String> tempPrefix = new ArrayList<>(prefixs);

        //enter new file
        ret.add(templateFactory.createEnterNewFileST(whileName, false));

        //reset prefix
        prefixs = new ArrayList<>();

        ret.addAll(visit(body));
        ret.addAll(visit(expr));

        // make recursive call if condition is true
        ret.add(templateFactory.createFuncCallST(whileName, false, false,
                "execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run "));

        //exit file
        ret.add(templateFactory.createExitFileST());

        // call while file if condition is true
        prefixs = new ArrayList<>(tempPrefix);
        prefixs.add("execute if score @s " + templateFactory.getExprCounterString() + " matches 1 run ");
        ret.add(templateFactory.createFuncCallST(whileName, false, false, getPrefix()));

        return ret;
    }

    /**
     * When an error occurred in codegeneration
     * Throw new RuntimeException
     * @param errMsg String Message to display on throw
     */
    private void Error(String errMsg){
        throw new RuntimeException(errMsg);
    }
    //endregion
}
