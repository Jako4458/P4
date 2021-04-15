package templates.expressions;

import org.stringtemplate.v4.ST;
import templates.Template;

public class ArithmeticExprST implements Template {
    private String output;

    /* Assumes that operator is a string in {+, -, *, /, %, Pow} */
    public ArithmeticExprST(String a, int b, String operator, String prefix, String exprID) {
        if (operator.equals("Pow"))
            this.output = createPowTemplate(a, b, prefix, exprID).render();
    }

    public ArithmeticExprST(String a, String b, String operator, String prefix, String exprID) {
        if (!operator.equals("Pow")) {
            this.output = createArithmeticTemplate(a, b, prefix, exprID).render();
        }
    }

    private ST createArithmeticTemplate(String a, String b, String prefix, String exprID) {
        ST template = new ST(
                "<prefix> scoreboard objectives add <exprID> dummy\n" +
                "<prefix> scoreboard players operation @s <exprID> = @s <aID>\n" +
                        "<prefix> scoreboard players operation @s <exprID> <op>= @s <bID>"
        );
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("exprID", exprID);
        return template;
    }

    private ST createPowTemplate(String a, int b, String prefix, String exprID) {
        ST template;
        if (b == 0) {
            template = new ST("scoreboard objectives add <exprID> dummy\nscoreboard players set @s <exprID> 1");
            template.add("exprID", exprID);
        } else if (b > 0) {
            template = new ST("scoreboard objectives add <exprID> dummy\nscoreboard players operation @s <exprID> = @s <aID>\n<follow>");
            template.add("exprID", exprID);
            template.add("aID", a);
            String follow = "";
            for (int i = b - 1; i > 0; i--) {
                follow = follow.concat(String.format("scoreboard players operation @s %s *= @s %s\n", exprID, a));
            }
            template.add("follow", follow);
        } else {
            template = new ST("scoreboard objectives add <exprID> dummy\nscoreboard players set @s <exprID> 1\n<follow>");
            template.add("exprID", exprID);
            String follow = "";
            for (int i = b; i < 0; i++) {
                follow = follow.concat(String.format("scoreboard players operation @s %s /= @s %s\n", exprID, a));
            }
            template.add("follow", follow);
        }
        return template;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
