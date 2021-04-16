import org.stringtemplate.v4.ST;

public class NegationExprST implements Template {
    private final String output;

    public NegationExprST(String a, String operator, String prefix, String exprID) {
        if (operator.equals("-"))
            this.output = generateNegativeTemplate(a, prefix, exprID).render();
        else if (operator.equals("not"))
            this.output = generateNegationTemplate(a, prefix, exprID).render();
        else
            this.output = "";
    }

    private ST generateNegativeTemplate(String a, String prefix, String exprID) {
        ST template = new ST(
                "<prefix> scoreboard objectives add <exprID> dummy\n" +
                        "<prefix> scoreboard players set @s <exprID> 0\n" +
                        "<prefix> scoreboard players operation @s <exprID> -= <aID>"
        );
        template.add("prefix", prefix);
        template.add("exprID", exprID);
        template.add("aID", a);

        return template;
    }

    private ST generateNegationTemplate(String a, String prefix, String exprID) {
        ST template = new ST(
                "<prefix> scoreboard objectives add <exprID> dummy\n" +
                        "<prefix> execute if score @s <aID> matches 1..1 run scoreboard players set @s <exprID> 0\n" +
                        "<prefix> execute if score @s <aID> matches 0..0 run scoreboard players set @s <exprID> 1"
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
