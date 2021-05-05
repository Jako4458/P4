import logging.Logger;

import java.util.ArrayList;

public class InfiniteLoopDetectionVisitor extends MinespeakBaseVisitor<ArrayList<SymEntry>> {

    private Scope currentScope;
    private LogFactory logFactory = new LogFactory();

    @Override
    public ArrayList<SymEntry> visitProg(MinespeakParser.ProgContext ctx) {
        enterScope(currentScope);
        return super.visitProg(ctx);
    }

    @Override
    public ArrayList<SymEntry> visitFactor(MinespeakParser.FactorContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();

        if(ctx.expr() != null) {
            ret.addAll(visit(ctx.expr()));
        } else if (ctx.rvalue() != null) {
            ret.addAll(visit(ctx.rvalue()));
        }

        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        return visit(ctx.factor());
    }

    @Override
    public ArrayList<SymEntry> visitRvalue(MinespeakParser.RvalueContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.add(currentScope.lookup(ctx.ID().getText()));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitEquality(MinespeakParser.EqualityContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitRelations(MinespeakParser.RelationsContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitPow(MinespeakParser.PowContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitAddSub(MinespeakParser.AddSubContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitAnd(MinespeakParser.AndContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitOr(MinespeakParser.OrContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        ret.addAll(visit(ctx.expr(0)));
        ret.addAll(visit(ctx.expr(1)));
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitForStmnt(MinespeakParser.ForStmntContext ctx) {
        enterScope(ctx.scope);
        ArrayList<SymEntry> exprRValues = visit(ctx.expr());
        ArrayList<SymEntry> assign = visit(ctx.assign());
        ArrayList<SymEntry> bodyLValues = visit(ctx.body());

        boolean reportError = true;

        for (SymEntry entry : exprRValues) {
            if(entry.equals(assign.get(0))) {
                reportError = false;
                break;
            }

            for (SymEntry LValues : bodyLValues) {
                if(entry.equals(LValues)) {
                    reportError = false;
                    break;
                }
            }
        }

        if (reportError) {
            Logger.shared.add(logFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        bodyLValues.addAll(assign);
        exitScope();
        return bodyLValues;
    }

    @Override
    public ArrayList<SymEntry> visitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        enterScope(ctx.scope);
        ArrayList<SymEntry> exprRValues = visit(ctx.expr());
        ArrayList<SymEntry> bodyLValues = visit(ctx.body());

        boolean reportError = true;

        for (SymEntry entry : exprRValues) {
            for (SymEntry LValues : bodyLValues) {
                if(entry.equals(LValues)) {
                    reportError = false;
                    break;
                }
            }
        }

        if (reportError) {
            Logger.shared.add(logFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        exitScope();
        return bodyLValues;
    }

    @Override
    public ArrayList<SymEntry> visitDoWhile(MinespeakParser.DoWhileContext ctx) {
        if (ctx.expr().getText().equals("true")) {
            Logger.shared.add(logFactory.createInfiniteLoopWarning(ctx.expr().getText(), ctx.expr()));
            return new ArrayList<>();
        } else if (ctx.expr().getText().equals("false")) {
            Logger.shared.add(logFactory.createUnreachableCodeWarning(ctx.expr().getText(), ctx.expr()));
            return new ArrayList<>();
        }

        enterScope(ctx.scope);
        ArrayList<SymEntry> exprRValues = visit(ctx.expr());
        ArrayList<SymEntry> bodyLValues = visit(ctx.body());

        boolean reportError = true;

        for (SymEntry entry : exprRValues) {
            for (SymEntry LValues : bodyLValues) {
                if(entry.equals(LValues)) {
                    reportError = false;
                    break;
                }
            }
        }

        if (reportError) {
            Logger.shared.add(logFactory.createConstantLoopExpressionWarning(ctx.expr().getText(), ctx.expr()));
        }

        exitScope();
        return bodyLValues;
    }

    @Override
    public ArrayList<SymEntry> visitBody(MinespeakParser.BodyContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        enterScope(ctx.scope);
        if (ctx.stmnts() != null)
            ret = visit(ctx.stmnts());
        exitScope();
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitStmnts(MinespeakParser.StmntsContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        for (MinespeakParser.StmntContext stmnt : ctx.stmnt()) {
            ret.addAll(visit(stmnt));
        }
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitStmnt(MinespeakParser.StmntContext ctx) {
        ArrayList<SymEntry> ret = new ArrayList<>();
        if (ctx.MCStmnt() == null) {
            ArrayList<SymEntry> temp = visit(ctx.children.get(0));
            if (temp != null)
                ret.addAll(temp);
        }
        return ret;
    }

    @Override
    public ArrayList<SymEntry> visitAssign(MinespeakParser.AssignContext ctx) {
        ArrayList<SymEntry> assignedValues = new ArrayList<>();
        SymEntry lookup = currentScope.lookup(ctx.ID().getText());
        assignedValues.add(lookup);
        return assignedValues;
    }

    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }
}
