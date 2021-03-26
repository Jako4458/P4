import Logging.Logger;
import Logging.TypeErrorLog;
import Logging.TypeWarningLog;
import Logging.WarningLog;

import java.util.List;

public class TypeCheckListener extends MinespeakBaseListener {

    public TypeCheckListener() {
    }

    @Override
    public void exitAddSub(MinespeakParser.AddSubContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if(ctx.ADD() != null){
            ctx.type = Type.inferType(left, MinespeakParser.ADD, right);
        } else if (ctx.SUB() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.SUB, right);
        }
    }

    @Override
    public void exitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        ctx.type = ctx.factor().type;
    }

    @Override
    public void exitFactor(MinespeakParser.FactorContext ctx) {
        ctx.type = ctx.literal().type;
    }

    @Override
    public void exitPow(MinespeakParser.PowContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.POW, right);
    }

    @Override
    public void exitOr(MinespeakParser.OrContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.OR, right);
    }

    @Override
    public void exitAnd(MinespeakParser.AndContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.AND, right);
    }

    @Override
    public void exitEquality(MinespeakParser.EqualityContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.EQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.EQUAL, right);
        } else if(ctx.NOTEQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.NOTEQUAL, right);
        }

    }

    @Override
    public void exitRelations(MinespeakParser.RelationsContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.LESSER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSER, right);
        } else if(ctx.GREATER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATER, right);
        } else if(ctx.LESSEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSEQ, right);
        } else if(ctx.GREATEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATEQ, right);
        }
    }

    @Override
    public void exitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        super.exitMulDivMod(ctx);
    }

    @Override
    public void exitLiteral(MinespeakParser.LiteralContext ctx) {
        if(ctx.booleanLiteral() != null) {
            ctx.type = Type._bool;
        } else if(ctx.BlockLiteral() != null) {
            ctx.type = Type._block;
        } else if(ctx.numberLiteral() != null) {
            ctx.type = Type._num;
        } else if(ctx.StringLiteral() != null) {
            ctx.type = Type._num;
        } else if(ctx.vector2Literal() != null) {
            ctx.type = Type._vector2;
        } else if(ctx.vector3Literal() != null) {
            ctx.type = Type._vector3;
        }

        //log error
    }

    /*@Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(new TypeErrorLog("expression error: '" + ctx.expr().getText() + "' is not a boolean expression"));
        }
    }

    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(new TypeErrorLog("expression error: '" + ctx.expr().getText() + "' is not a boolean expression"));
        }
    }

    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        if(ctx.assign().type != Type._num){
            Logger.shared.add(new TypeWarningLog("expression error: '" + ctx.expr().getText() + "' is not a number"));
        }

        if(ctx.expr().type != Type._bool){
            Logger.shared.add(new TypeErrorLog("expression error: '" + ctx.expr().getText() + "' is not a boolean expression"));
        }

        System.out.println(ctx.getText());
    }*/

    /*@Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(new TypeErrorLog("expression error: '" + ctx.expr().getText() + "' is not a boolean expression"));
        }
    }*/
}
