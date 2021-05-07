import logging.Logger;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class UnassignedVariableListener extends MinespeakBaseListener {
    private Map<SymEntry, Boolean> declaredVariables = new HashMap<>();
    private Scope currentScope;
    private LogFactory logFac = new LogFactory();

    public UnassignedVariableListener(){
        enterScope(null);
    }

    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        enterScope(ctx.scope);
    }

    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {

        for (Map.Entry<SymEntry, Boolean> entry : declaredVariables.entrySet()) {
            int i = 0;
            boolean assigned = entry.getValue();
            SymEntry symEntry = entry.getKey();
            if(assigned)
                return;

            Logger.shared.add(logFac.createUnassignedVariableWarningLog(symEntry.getName(), symEntry.getCtx()));
        }
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

    @Override
    public void exitDcls(MinespeakParser.DclsContext ctx) {
        for (TerminalNode dcl : ctx.ID()){
            String name = dcl.getText();
            SymEntry entry = currentScope.lookup(name);

            if (declaredVariables.containsKey(entry))
                return;

            declaredVariables.put(entry, false);
        }
    }

    @Override
    public void exitAssign(MinespeakParser.AssignContext ctx) {
        SymEntry entry = currentScope.lookup(ctx.ID().getText());
        declaredVariables.replace(entry, true);
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }
}

