import org.stringtemplate.v4.ST;

public class LogicalExprST implements Template {
    private String output;

    public LogicalExprST(String a, String b, String operator, String prefix, String exprID, String tempID, boolean setComment) {
        if (operator.equals("and"))
            this.output = generateAndTemplate(a, b, prefix, exprID, tempID, setComment).render();
        else if (operator.equals("or"))
            this.output = generateOrTemplate(a, b, prefix, exprID, tempID, setComment).render();
    }

    private ST generateAndTemplate(String a, String b, String prefix, String exprID, String tempID, boolean setComment) {
        ST template = new ST("<Comment>" +
                        "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard objectives add <tempID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>scoreboard players set @s <tempID> 0\n" +
                        "<prefix>scoreboard players operation @s <tempID> += @s <aID>\n" +
                        "<prefix>scoreboard players operation @s <tempID> += @s <bID>\n" +
                        "<prefix>execute if score @s <tempID> matches 2 run scoreboard players set @s <exprID> 1\n" +
                        "<prefix>scoreboard objectives remove <tempID>\n"
        );

        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+" And\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);
        template.add("exprID", exprID);
        template.add("tempID", tempID);
        template.add("aID", a);
        template.add("bID", b);
        return template;
    }

    private ST generateOrTemplate(String a, String b, String prefix, String exprID, String tempID, boolean setComment) {
        ST template = new ST("<Comment>" +
                        "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard objectives add <tempID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>scoreboard players set @s <tempID> 0\n" +
                        "<prefix>scoreboard players operation @s <tempID> += @s <aID>\n" +
                        "<prefix>scoreboard players operation @s <tempID> += @s <bID>\n" +
                        "<prefix>execute if score @s <tempID> matches 1.. run scoreboard players set @s <exprID> 1\n" +
                        "<prefix>scoreboard objectives remove <tempID>\n"
        );

        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+" Or\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);
        template.add("exprID", exprID);
        template.add("tempID", tempID);
        template.add("aID", a);
        template.add("bID", b);
        return template;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
