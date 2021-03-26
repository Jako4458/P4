import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ScopeVisitorDepre extends MinespeakBaseVisitor<MSValue> {
   private Scope scope;
    private Map<String, Function> functions;

    public ScopeVisitorDepre(Scope scope) {
        this.scope = scope;
        this.functions = new HashMap<>();
    }

    public ScopeVisitorDepre(){

    }

    public Map<String, Function> getFunctions() {
        return this.functions;
    }

    public Scope getScope() {
        return this.scope;
    }
/*
    @Override
    public MSValue visitProg(MinespeakParser.ProgContext ctx) {
        return this.visit(ctx.blocks());
    }

    @Override
    public MSValue visitBlocks(MinespeakParser.BlocksContext ctx) {
        ctx.block().forEach(this::visit);   // experimental code, might not work
        return MSValue.VOID;
    }

    @Override
    public MSValue visitBlock(MinespeakParser.BlockContext ctx) {
        if (ctx.func() != null)
            return this.visit(ctx.func());
        else if (ctx.mcFunc() != null)
            return this.visit(ctx.mcFunc());
        else
            return MSValue.ERROR;
    }

    @Override
    public MSValue visitMcFunc(MinespeakParser.McFuncContext ctx) {
        return MSValue.VOID;
    }

    @Override
    public MSValue visitFunc(MinespeakParser.FuncContext ctx) {
        List<TerminalNode> params = new ArrayList<>();
        List<MinespeakParser.ParamContext> rawParams = ctx.params().param();
        for (MinespeakParser.ParamContext param : rawParams) {
            params.add(param.ID());
        }
        ParseTree body = ctx.funcBody();
        String id = ctx.ID().getText();
        //functions.put(id, new Function(scope, params, body));
        
        this.scope = new Scope(this.scope);
        this.visit(ctx.params());
        this.scope = new Scope(this.scope);
        MSValue returnVal = this.visit(ctx.funcBody()); // Used for typechecking later on
        
        if (ctx.primType() == null)
            return MSValue.VOID;
        if (ctx.primType().primitiveType() == null)
            return MSValue.ERROR;
        return MSValue.generateValueFromType(ctx.primType().primitiveType().getText());
    }

    @Override
    public MSValue visitParams(MinespeakParser.ParamsContext ctx) {
        ctx.param().forEach(this::visit);
        return MSValue.VOID;
    }

    @Override
    public MSValue visitParam(MinespeakParser.ParamContext ctx) {
        this.scope.addVariable(ctx.ID().getText(), MSValue.generateValueFromType(ctx.primType().getText()));
        return MSValue.VOID;
    }

    @Override
    public MSValue visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        this.visit(ctx.stmnts());
        if (ctx.retVal() == null)
            return MSValue.VOID;
        return this.visit(ctx.retVal());
    }

    @Override
    public MSValue visitRetVal(MinespeakParser.RetValContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public MSValue visitStmnts(MinespeakParser.StmntsContext ctx) {
        ctx.stmnt().forEach(this::visit);
        return MSValue.VOID;
    }

    @Override
    public MSValue visitDclsStmnt(MinespeakParser.DclsStmntContext ctx) {
        return this.visit(ctx.dcls());
    }

    @Override
    public MSValue visitAssignStmnt(MinespeakParser.AssignStmntContext ctx) {
        return this.visit(ctx.assign());
    }

    @Override
    public MSValue visitInstanStmnt(MinespeakParser.InstanStmntContext ctx) {
        return this.visit(ctx.instan());
    }

    @Override
    public MSValue visitIfStmntStmnt(MinespeakParser.IfStmntStmntContext ctx) {
        return this.visit(ctx.ifStmnt());
    }

    @Override
    public MSValue visitLoopStmnt(MinespeakParser.LoopStmntContext ctx) {
        return this.visit(ctx.loop());
    }

    @Override
    public MSValue visitMCStmntStmnt(MinespeakParser.MCStmntStmntContext ctx) {
        return this.visit(ctx.MCStmnt());
    }

    @Override
    public MSValue visitFuncCallStmnt(MinespeakParser.FuncCallStmntContext ctx) {

        return this.visit(ctx.funcCall());
    }

    @Override
    public MSValue visitLoop(MinespeakParser.LoopContext ctx) {
        return super.visitLoop(ctx);
    }

    @Override
    public MSValue visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        return super.visitDoWhile(ctx);
    }

    @Override
    public MSValue visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        return super.visitWhileStmnt(ctx);
    }

    @Override
    public MSValue visitForeach(MinespeakParser.ForeachContext ctx) {
        return super.visitForeach(ctx);
    }

    @Override
    public MSValue visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        //int start = this.visit



        return super.visitForStmnt(ctx);
    }

    @Override
    public MSValue visitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        int length = ctx.expr().size();
        for (int i = 0; i < length; i++) {
            if (this.visit(ctx.expr(i)).getBooleanValue())
                return this.visit(ctx.stmnts(i));
        }

        if (ctx.stmnts().size() > ctx.expr().size())
            return this.visit(ctx.stmnts(ctx.stmnts().size()-1)); // else statement

        return MSValue.VOID;
    }

    @Override
    public MSValue visitDcls(MinespeakParser.DclsContext ctx) {
        return super.visitDcls(ctx);
    }

    @Override
    public MSValue visitInstan(MinespeakParser.InstanContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++) {
            scope.addVariable(ctx.ID(i).getText(), this.visit(ctx.expr(i)));
        }
        return MSValue.generateValueFromType(ctx.primType().primitiveType().getText());
    }

    @Override
    public MSValue visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public MSValue visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        return super.visitNotNegFac(ctx);
    }

    @Override
    public MSValue visitOr(MinespeakParser.OrContext ctx) {
        return super.visitOr(ctx);
    }

    @Override
    public MSValue visitAddSub(MinespeakParser.AddSubContext ctx) {
        MSValue lhand = this.visit(ctx.expr(0));
        MSValue rhand = this.visit(ctx.expr(1));
        MSValue result = MSValue.ERROR;
        switch (ctx.op.getType()){
            case MinespeakLexer.ADD:
                result = new MSValue(lhand.getValueAsInt() + rhand.getValueAsInt());
                break;
            case MinespeakLexer.SUB:
                result = new MSValue(lhand.getValueAsInt() - rhand.getValueAsInt());
            default:
                break;
        }

        return result;
    }

    @Override
    public MSValue visitAnd(MinespeakParser.AndContext ctx) {
        return super.visitAnd(ctx);
    }

    @Override
    public MSValue visitPow(MinespeakParser.PowContext ctx) {
        MSValue lhand = this.visit(ctx.expr(0));
        MSValue rhand = this.visit(ctx.expr(1));
        MSValue result = MSValue.ERROR;

        result = new MSValue( (int) Math.pow(lhand.getValueAsInt(), rhand.getValueAsInt()));
        switch (ctx.op.getType()){
            case MinespeakLexer.ADD:
                result = new MSValue(lhand.getValueAsInt() + rhand.getValueAsInt());
                break;
            case MinespeakLexer.SUB:
                result = new MSValue(lhand.getValueAsInt() - rhand.getValueAsInt());
            default:
                break;
        }
        return result;
    }

    @Override
    public MSValue visitRelations(MinespeakParser.RelationsContext ctx) {
        return super.visitRelations(ctx);
    }

    @Override
    public MSValue visitEquality(MinespeakParser.EqualityContext ctx) {
        return super.visitEquality(ctx);
    }

    @Override
    public MSValue visitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.literal() != null)
            return this.visit(ctx.literal());
        if (ctx.ID() != null)
            return new MSValue(ctx.ID().getText());
        return new MSValue(ctx.expr());
    }

    @Override
    public MSValue visitFuncCall(MinespeakParser.FuncCallContext ctx) {
        return super.visitFuncCall(ctx);
    }

    @Override
    public MSValue visitAssign(MinespeakParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        if(scope.lookup(id) == null)
            return MSValue.ERROR;

        MinespeakParser.CompAssignContext compAssignNode = ctx.compAssign();
        MSValue expr = this.visit(ctx.expr());

        if(compAssignNode != null) {
            String symbol = compAssignNode.op.getText();
            MSValue left = scope.lookup(id);
            switch (compAssignNode.getSymbol().getText()) {
                case "+=":
                    break;
                case "-=":
                    break;
                case "%=":
                    break;
                case "/=":
                    break;
                case "*=":
                    break;
            }
        } else {
            scope.reAssign(id, expr);
        }

        return MSValue.VOID;
    }

    @Override
    public MSValue visitPrimType(MinespeakParser.PrimTypeContext ctx) {
        return super.visitPrimType(ctx);
    }

    @Override
    public MSValue visitLiteral(MinespeakParser.LiteralContext ctx) {
        if (ctx.BlockLiteral() != null)
            return new MSValue(ctx.BlockLiteral().getText());
        else if (ctx.booleanLiteral() != null)
            return this.visit(ctx.booleanLiteral());
        else if (ctx.StringLiteral() != null)
            return new MSValue(ctx.StringLiteral().getText());
        else if (ctx.vector2Literal() != null)
            return this.visit(ctx.vector2Literal());
        else if (ctx.vector3Literal() != null)
            return this.visit(ctx.vector3Literal());
        else if (ctx.numberLiteral() != null)
            return this.visit(ctx.numberLiteral());

        return MSValue.ERROR;
    }

    @Override
    public MSValue visitAccess(MinespeakParser.AccessContext ctx) {
        return super.visitAccess(ctx);
    }

    @Override
    public MSValue visitCompAssign(MinespeakParser.CompAssignContext ctx) {
        return super.visitCompAssign(ctx);
    }

    @Override
    public MSValue visitPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public MSValue visitBooleanLiteral(MinespeakParser.BooleanLiteralContext ctx) {
        return super.visitBooleanLiteral(ctx);
    }

    @Override
    public MSValue visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx) {
        return new MSValue(ctx.DecimalDigit().getText());
    }

    @Override
    public MSValue visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        return super.visitVector2Literal(ctx);
    }

    @Override
    public MSValue visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        return super.visitVector3Literal(ctx);
    }

    @Override
    public MSValue visitNewlines(MinespeakParser.NewlinesContext ctx) {
        return MSValue.VOID;
    }
    */

}
