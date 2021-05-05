import org.stringtemplate.v4.ST;

public class RelationExprST implements Template {
    private String output;


    public RelationExprST(String a, String b, String operator, String prefix, String exprID, boolean setComment) {
        ST template = new ST("<Comment>" +
                       "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>execute if score @s <aID> <operator> @s <bID> run scoreboard players set @s <exprID> 1\n"
        );

        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("exprID", exprID);
        template.add("operator", operator);
        this.output = template.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
