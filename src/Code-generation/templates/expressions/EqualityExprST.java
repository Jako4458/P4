import org.stringtemplate.v4.ST;

public class EqualityExprST implements Template {
    private String output;

    public EqualityExprST(String a, String b, String operator, String prefix, String exprID) {
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
        this.output = template.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
