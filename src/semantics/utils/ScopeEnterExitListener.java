public class ScopeEnterExitListener extends MinespeakBaseListener {
    protected Scope currentScope;

    public ScopeEnterExitListener() {
        enterScope(null);
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {
        exitScope();
    }

    @Override
    public void enterBlock(MinespeakParser.BlockContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitBlock(MinespeakParser.BlockContext ctx) {
        exitScope();
    }

    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        exitScope();
    }

    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        exitScope();
    }


    @Override
    public void enterDoWhile(MinespeakParser.DoWhileContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        exitScope();
    }

    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterForeach(MinespeakParser.ForeachContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        exitScope();
    }

    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterIfStmnt(MinespeakParser.IfStmntContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        exitScope();
    }

    @Override
    public void enterBody(MinespeakParser.BodyContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitBody(MinespeakParser.BodyContext ctx) {
        exitScope();
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    private Scope getCurrentScope(){
        return currentScope;
    }
}
