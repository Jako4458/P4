package templates.expressions;

import org.stringtemplate.v4.ST;
import templates.Template;

public class ArithmeticExprST implements Template {
    private String output;

    /* Assumes that operator is a string in {+, -, *, /, %, Pow} */
    public ArithmeticExprST(int a, int b, String operator, String prefix, String exprID) {
        ST template;
        if (operator.equals("Pow")) {
            if (b == 0) {
                template = new ST("scoreboard objectives add <exprID> dummy\nscoreboard players set @s <exprID> 1");
                template.add("exprID", exprID);
            } else if (b > 0) {
                template = new ST("scoreboard objectives add <exprID> dummy\nscoreboard players set @s <exprID> <a>");
            }
        } else {

        }
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
