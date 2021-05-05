
import org.stringtemplate.v4.ST;

public class ArithmeticExprST implements Template {
    private String output;

    /* Assumes that operator is a string in {+, -, *, /, %, Pow} */
    public ArithmeticExprST(String a, int b, String operator, String prefix, String exprID, boolean setComment) {
        if (operator.equals("Pow"))
            this.output = createPowTemplate(a, b, prefix, exprID, setComment).render();
    }

    public ArithmeticExprST(String a, String b, String operator, String exprID, Type t1, Type t2, String prefix, boolean setComment) {
        if (t1 == Type._vector2) {
            if (t2 == Type._vector2)
                this.output = createArithmeticVector2Template(a, b, operator, exprID, prefix, setComment).render();
            else if (t2 == Type._num)
                this.output = createArithmeticVector2ScaleTemplate(a, b, operator, exprID, prefix, setComment).render();
        } else if (t2 == Type._vector2) {
            if (t1 == Type._num)
                this.output = createArithmeticScaleVector2Template(a, b, operator, exprID, prefix, setComment).render();
        } else if (t1 == Type._vector3) {
            if (t2 == Type._vector3)
                this.output = createArithmeticVector3Template(a, b, operator, exprID, prefix, setComment).render();
            else if (t2 == Type._num)
                this.output = createArithmeticVector3ScaleTemplate(a, b, operator, exprID, prefix, setComment).render();
        } else if (t2 == Type._vector3) {
            if (t1 == Type._num)
                this.output = createArithmeticScaleVector3Template(a, b, operator, exprID, prefix, setComment).render();
        } else if (t1 == Type._num && t2 == Type._num) {
            this.output = createArithmeticTemplate(a, b, operator, exprID, prefix, setComment).render();
        }
    }

    // Assumes both operands are vector3 and that the operator is either + or -
    private ST createArithmeticVector3Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");

        template.add("Comment", setComment ? "#Vector3 "+this.getClass().toString().substring(6)+"+ or -\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a + "_x", b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b + "_y", operator, exprID + "_y", prefix, false).render());
        template.add("Z", createArithmeticTemplate(a + "_z", b + "_z", operator, exprID + "_z", prefix, false).render());
        return template;
    }

    // Assumes both operands are vector2 and that the operator is either + or -
    private ST createArithmeticVector2Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 "+this.getClass().toString().substring(6)+" + or -\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a + "_x", b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b + "_y", operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is number and second operand is vector2 and operator is *
    private ST createArithmeticScaleVector2Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 scale "+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a, b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a, b + "_y", operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is vector2 and second operand is number and operator is *
    private ST createArithmeticVector2ScaleTemplate(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 scale "+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a + "_x", b, operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b, operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is vector3 and second operand is number and operator is *
    private ST createArithmeticVector3ScaleTemplate(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");

        template.add("Comment", setComment ? "#Vector3 scale "+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a + "_x", b, operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b, operator, exprID + "_y", prefix, false).render());
        template.add("Z", createArithmeticTemplate(a + "_z", b, operator, exprID + "_z", prefix, false).render());
        return template;
    }

    // Assumes first operand is number and second operand is vector3 and operator is *
    private ST createArithmeticScaleVector3Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");

        template.add("Comment", setComment ? "#Vector3 scale "+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("X", createArithmeticTemplate(a, b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a, b + "_y", operator, exprID + "_y", prefix, false).render());
        template.add("Z", createArithmeticTemplate(a, b + "_z", operator, exprID + "_z", prefix, false).render());
        return template;
    }

    private ST createArithmeticTemplate(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment>" +
                        "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players operation @s <exprID> = @s <aID>\n" +
                        "<prefix>scoreboard players operation @s <exprID> <op>= @s <bID>\n"
        );
        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+" " + operator + "\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("op", operator);
        template.add("exprID", exprID);
        return template;
    }

    private ST createPowTemplate(String a, int b, String prefix, String exprID, boolean setComment) {
        ST template;
        String follow = "";

        if (b > 0) {
            template = new ST("<Comment>" +
                            "<prefix>scoreboard objectives add <exprID> dummy\n" +
                            "<prefix>scoreboard players operation @s <exprID> = @s <aID>\n" +
                            "<follow>");

            template.add("aID", a);
            for (int i = b - 1; i > 0; i--) {
                follow = follow.concat(String.format("%sscoreboard players operation @s %s *= @s %s\n", prefix, exprID, a));
            }
        } else {
            template = new ST("<Comment>" +
                            "<prefix>scoreboard objectives add <exprID> dummy\n" +
                            "<prefix>scoreboard players set @s <exprID> 1\n" +
                            "<follow>");
            for (int i = b; i < 0; i++) {
                follow = follow.concat(String.format("%sscoreboard players operation @s %s /= @s %s\n", prefix, exprID, a));
            }
        }
        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"POW\n" : ""); //substring to remove "class "
        template.add("exprID", exprID);
        template.add("follow", follow);
        template.add("prefix", prefix);
        return template;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
