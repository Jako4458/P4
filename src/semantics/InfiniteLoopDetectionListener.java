import logging.Logger;
import logging.logs.Log;

import java.util.ArrayList;
/*
  Does not work. Replaced by InfiniteLoopDetectionVisitor
* */
public class InfiniteLoopDetectionListener extends ScopeEnterExitListener {
    ArrayList<ArrayList<SymEntry>> valueNotAssigned = new ArrayList<>();
    LogFactory LogFactory = new LogFactory();
    int depth = -1;

    @Override
    public void enterDoWhile(MinespeakParser.DoWhileContext ctx) {
        depth++;
        createNewDepthList();
        super.enterDoWhile(ctx);
    }

    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        if (ctx.expr().getText().equals("true")) {
            Logger.shared.add(LogFactory.createInfiniteLoopWarning(ctx.expr().getText(), ctx.expr()));
        } else if (ctx.expr().getText().equals("false")) {
            Logger.shared.add(LogFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        ArrayList<SymEntry> symEntries = valueNotAssigned.get(depth);

        if (symEntries.size() != 0) {
            Logger.shared.add(LogFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        depth--;
        super.exitDoWhile(ctx);
    }

    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        depth++;
        createNewDepthList();
        super.enterForStmnt(ctx);
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        if (ctx.expr().getText().equals("true")) {
            Logger.shared.add(LogFactory.createInfiniteLoopWarning(ctx.expr().getText(), ctx.expr()));
        } else if (ctx.expr().getText().equals("false")) {
            Logger.shared.add(LogFactory.createUnreachableCodeWarning(ctx.expr().getText(), ctx.expr()));
        }

        ArrayList<SymEntry> symEntries = valueNotAssigned.get(depth);

        if (symEntries.size() != 0) {
            Logger.shared.add(LogFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        depth--;

        super.exitForStmnt(ctx);
    }

    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        depth++;
        createNewDepthList();
        super.enterWhileStmnt(ctx);
    }

    private void createNewDepthList() {
        if (valueNotAssigned.size() == depth) {
            valueNotAssigned.add(new ArrayList<>());
        } else {
            valueNotAssigned.set(depth, new ArrayList<>());
        }
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        if (ctx.expr().getText().equals("true")) {
            Logger.shared.add(LogFactory.createInfiniteLoopWarning(ctx.expr().getText(), ctx.expr()));
        } else if (ctx.expr().getText().equals("false")) {
            Logger.shared.add(LogFactory.createUnreachableCodeWarning(ctx.expr().getText(), ctx.expr()));
        }

        ArrayList<SymEntry> symEntries = valueNotAssigned.get(depth);

        if (symEntries.size() != 0) {
            Logger.shared.add(LogFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        depth--;
        super.exitWhileStmnt(ctx);
    }

    @Override
    public void exitRvalue(MinespeakParser.RvalueContext ctx) {
        if (depth < 0) {
            return;
        }
        SymEntry lookup = currentScope.lookup(ctx.ID().getText());

        valueNotAssigned.get(depth).add(lookup);
    }

    @Override
    public void exitAssign(MinespeakParser.AssignContext ctx) {
        SymEntry lookup = currentScope.lookup(ctx.ID().getText());
        for (var list : valueNotAssigned) {
            list.remove(lookup);
        }
    }
}
