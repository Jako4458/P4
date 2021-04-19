import org.stringtemplate.v4.ST;

public class EqualityExprST implements Template {
    private String output;

    public EqualityExprST(String a, String b, String operator, String exprID, Type type, String prefix) {
        if (type == Type._num)
            this.output = createNumEqualityST(a, b, operator, exprID, prefix).render();
        else if (type == Type._vector2)
            this.output = createVector2EqualityST(a, b, operator, exprID, prefix).render();
        else if (type == Type._vector3)
            this.output = createVector3EqualityST(a, b, operator, exprID, prefix).render();
    }

    public EqualityExprST(String a, String b, String operator, String exprID, Type t1, Type t2, int size, String prefix) {
        this.output = createArrayEqualityST(a, b, operator, exprID, size, prefix).render();
    }

    private ST createNumEqualityST(String a, String b, String operator, String exprID, String prefix) {
        ST template = new ST(
                "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>execute <condition> score @s <aID> = @s <bID> run scoreboard players set @s <exprID> 1\n"
        );
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("exprID", exprID);
        template.add("condition", operator.equals("==") ? "if" : "unless");
        return template;
    }

    // Assumes both arrays are of same length, and both have base type num
    private ST createArrayEqualityST(String a, String b, String operator, String exprID, int size, String prefix) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < size; i++) {
            temp.append(String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0\n",
                    prefix, operator.equals("==") ? "unless" : "if", a + "_" + i, b + "_" + i, exprID));
        }

        String start = new InstanST(exprID, 1, prefix).getOutput();

        return new ST(start + temp);
    }

    private ST createVector2EqualityST(String a, String b, String operator, String exprID, String prefix) {
        ST template = new ST("<start><X><Y>");

        template.add("start", new InstanST(exprID, 1, prefix).getOutput());
        template.add("X", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_x", b + "_x", exprID));
        template.add("Y", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_y", b + "_y", exprID));

        return template;
    }

    private ST createVector3EqualityST(String a, String b, String operator, String exprID, String prefix) {
        ST template = new ST("<start><Z>");

        template.add("start", createVector2EqualityST(a, b, operator, exprID, prefix));
        template.add("Z", String.format("%sexecute %s score @s %s = @s %s run scoreboard players set @s %s 0",
                prefix, operator.equals("==") ? "unless" : "if", a + "_z", b + "_z", exprID));

        return template;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
