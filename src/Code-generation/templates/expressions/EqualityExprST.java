import org.stringtemplate.v4.ST;

public class EqualityExprST implements Template {
    private String output;

    public EqualityExprST(String a, String b, String operator, String exprID, Type type, String prefix, boolean setComment) {
        if (type == Type._num)
            this.output = createNumEqualityST(a, b, operator, exprID, prefix, setComment).render();
        else if (type == Type._vector2)
            this.output = createVector2EqualityST(a, b, operator, exprID, prefix, setComment).render();
        else if (type == Type._vector3)
            this.output = createVector3EqualityST(a, b, operator, exprID, prefix, setComment).render();
    }

    public EqualityExprST(String a, String b, String operator, String exprID, Vector3 pos1, Vector3 pos2, String prefix, boolean setComment) {
        ST template = new ST("<Comment>" +
                        "<prefix>execute as @e[tag=<a>] at @e[tag=<a>] run clone ~ ~-1 ~ ~ ~-1 ~ <pos1X> <pos1Y> <pos1Z>\n" +
                        "<prefix>execute as @e[tag=<b>] at @e[tag=<b>] run clone ~ ~-1 ~ ~ ~-1 ~ <pos2X> <pos2Y> <pos2Z>\n" +

                        "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>execute <operator> blocks <pos1X> <pos1Y> <pos1Z> <pos1X> <pos1Y> <pos1Z> <pos2X> <pos2Y> <pos2Z> " +
                        "run scoreboard players set @s <exprID> 1\n"
        );

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");

        template.add("prefix", prefix);

        template.add("a", a);
        template.add("b", b);
        template.add("exprID", exprID);

        template.add("pos1X", pos1.getX());
        template.add("pos1Y", pos1.getY());
        template.add("pos1Z", pos1.getZ());

        template.add("pos2X", pos2.getX());
        template.add("pos2Y", pos2.getY());
        template.add("pos2Z", pos2.getZ());

        template.add("operator", operator.equals("==") ? "if" :"unless");

        output = template.render();
    }

    private ST createNumEqualityST(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment>" +
                        "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>execute <condition> score @s <aID> = @s <bID> run scoreboard players set @s <exprID> 1\n"
        );

        template.add("Comment", setComment ? "#Num "+ this.getTemplateName() +"\n" : "");
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("exprID", exprID);
        template.add("condition", operator.equals("==") ? "if" : "unless");
        return template;
    }

    // Assumes both arrays are of same length, and both have base type num
    private ST createArrayEqualityST(String a, String b, String operator, String exprID, int size, String prefix, boolean setComment) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < size; i++) {
            temp.append(String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0\n",
                    prefix, operator.equals("==") ? "unless" : "if", a + "_" + i, b + "_" + i, exprID));
        }

        String start = new InstanST(exprID, 1, prefix, setComment).getOutput();

        return new ST(start + temp);
    }

    private ST createVector2EqualityST(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><start><X><Y>");

        template.add("Comment", setComment ? "#Vector2 "+ this.getTemplateName() +"\n" : "");
        template.add("start", new InstanST(exprID, 1, prefix, false).getOutput());
        template.add("X", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_x", b + "_x", exprID));
        template.add("Y", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_y", b + "_y", exprID));

        return template;
    }

    private ST createVector3EqualityST(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><start><Z>");

        template.add("Comment", setComment ? "#Vector3 "+ this.getTemplateName() +"\n" : "");
        template.add("start", createVector2EqualityST(a, b, operator, exprID, prefix, false));
        template.add("Z", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_z", b + "_z", exprID));

        return template;
    }

    private String getTemplateName(){
        return this.getClass().toString().substring(6); //substring to remove "class "
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
