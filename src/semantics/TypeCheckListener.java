public class TypeCheckListener extends MinespeakBaseListener {

    public TypeCheckListener() {
    }

    @Override
    public void exitAddSub(MinespeakParser.AddSubContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if(ctx.ADD() != null){
            ctx.type = Type.inferType(left, MinespeakParser.ADD, right);
        } else {
            ctx.type = Type.inferType(left, MinespeakParser.SUB, right);
        }
    }
}
