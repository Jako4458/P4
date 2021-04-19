import org.stringtemplate.v4.ST;

public class NegationExprST implements Template {
    private String output;

    public NegationExprST(String a, String operator, String prefix, String exprID, Type type) {
        if (operator.equals("-")){
            if (type == Type._num)
                this.output = generateNegativeNumTemplate(a, prefix, exprID).render();
            else if (type == Type._vector2)
                this.output = generateNegativeVec2Template(a, prefix, exprID).render();
            else if (type == Type._vector3)
                this.output = generateNegativeVec3Template(a, prefix, exprID).render();
        }
        else if (operator.equals("not"))
            this.output = generateNegationTemplate(a, prefix, exprID).render();
    }

    private ST generateNegativeNumTemplate(String a, String prefix, String exprID) {
        ST template = new ST(
                "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>scoreboard players set @s <exprID> 0\n" +
                        "<prefix>scoreboard players operation @s <exprID> -= <aID>\n"
        );
        template.add("prefix", prefix);
        template.add("exprID", exprID);
        template.add("aID", a);

        return template;
    }

    private ST generateNegativeVec2Template(String a, String prefix, String exprID) {
        ST template = new ST("<NegX><NegY>");

        NegationExprST negX = new NegationExprST(a + "_x", "-", prefix, exprID, Type._num);
        NegationExprST negY = new NegationExprST(a + "_y", "-", prefix, exprID, Type._num);

        template.add("NegX", negX.output);
        template.add("NegY", negY.output);

        return template;
    }
    private ST generateNegativeVec3Template(String a, String prefix, String exprID) {
        ST template = new ST("<NegX><NegY>");

        NegationExprST negX = new NegationExprST(a + "_x", "-", prefix, exprID, Type._num);
        NegationExprST negY = new NegationExprST(a + "_y", "-", prefix, exprID, Type._num);
        NegationExprST negZ = new NegationExprST(a + "_z", "-", prefix, exprID, Type._num);

        template.add("NegX", negX.output);
        template.add("NegY", negY.output);
        template.add("NegZ", negZ.output);

        return template;
    }

    private ST generateNegationTemplate(String a, String prefix, String exprID) {
        ST template = new ST(
                "<prefix>scoreboard objectives add <exprID> dummy\n" +
                        "<prefix>execute if score @s <aID> matches 1..1 run scoreboard players set @s <exprID> 0\n" +
                        "<prefix>execute if score @s <aID> matches 0..0 run scoreboard players set @s <exprID> 1\n"
        );
        template.add("prefix", prefix);
        template.add("exprID", exprID);
        template.add("aID", a);
        return template;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
