
import org.stringtemplate.v4.ST;

public class ArithmeticExprST implements Template {
    private String output;

    public ArithmeticExprST(ST template) {
        this.output = template.render();
    }

    /* Assumes that operator is a string in {+, -, *, /, %} */
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

        template.add("Comment", setComment ? "#Vector3 "+ this.getTemplateName() +"+ or -\n" : "");
        template.add("X", createArithmeticTemplate(a + "_x", b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b + "_y", operator, exprID + "_y", prefix, false).render());
        template.add("Z", createArithmeticTemplate(a + "_z", b + "_z", operator, exprID + "_z", prefix, false).render());
        return template;
    }

    // Assumes both operands are vector2 and that the operator is either + or -
    private ST createArithmeticVector2Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 "+ this.getTemplateName() +" + or -\n" : "");
        template.add("X", createArithmeticTemplate(a + "_x", b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b + "_y", operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is number and second operand is vector2 and operator is *
    private ST createArithmeticScaleVector2Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 scale "+ this.getTemplateName() +"\n" : "");
        template.add("X", createArithmeticTemplate(a, b + "_x", operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a, b + "_y", operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is vector2 and second operand is number and operator is *
    private ST createArithmeticVector2ScaleTemplate(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y>");

        template.add("Comment", setComment ? "#Vector2 scale "+ this.getTemplateName() +"\n" : "");
        template.add("X", createArithmeticTemplate(a + "_x", b, operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b, operator, exprID + "_y", prefix, false).render());
        return template;
    }

    // Assumes first operand is vector3 and second operand is number and operator is *
    private ST createArithmeticVector3ScaleTemplate(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");

        template.add("Comment", setComment ? "#Vector3 scale "+ this.getTemplateName() +"\n" : "");
        template.add("X", createArithmeticTemplate(a + "_x", b, operator, exprID + "_x", prefix, false).render());
        template.add("Y", createArithmeticTemplate(a + "_y", b, operator, exprID + "_y", prefix, false).render());
        template.add("Z", createArithmeticTemplate(a + "_z", b, operator, exprID + "_z", prefix, false).render());
        return template;
    }

    // Assumes first operand is number and second operand is vector3 and operator is *
    private ST createArithmeticScaleVector3Template(String a, String b, String operator, String exprID, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");

        template.add("Comment", setComment ? "#Vector3 scale "+ this.getTemplateName() +"\n" : "");
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
        template.add("Comment", setComment ? "#"+ this.getTemplateName() +" " + operator + "\n" : "");
        template.add("prefix", prefix);
        template.add("aID", a);
        template.add("bID", b);
        template.add("op", operator);
        template.add("exprID", exprID);
        return template;
    }

    public static ArithmeticExprST createPowSetupTemplate(String expr1, String expr2, String acc, String baseName, String counterName, String loopID ,String prefix, boolean setComment) {
            ST template = new ST(   "<Comment><prefix><instanAcc><instanCounter><instanBase>" +
                                            "<funcCall>" +
                                            "<assignIfZero><assignIfNegative>\n" +
                                            "<delCounter><delBase>");

        String instanCounter = new InstanST(counterName ,1, prefix, false).getOutput();
        String instanBase = new InstanST(baseName ,expr1, prefix, false).getOutput();
        String instanAcc = new InstanST(acc, expr1, prefix, false).getOutput();

        //set to 1 if expr2 is 0
        String assignIfZero = new AssignST(acc, 1, prefix + "execute as @s if score @s " + expr2 + " matches 0 run ", false).getOutput();
        //set to 0 if expr2 is negative
        String assignIfNegative = new AssignST(acc, 0, prefix + "execute as @s if score @s " + expr2 + " matches ..-1 run ", false).getOutput();

        //call power function
        String funcCall = FuncCallST.generateFuncCallToNonMC(loopID, prefix + "execute unless score @s " + expr2 + " matches 0..1 run ", false).getOutput();


        template.add("Comment", setComment ? "# Pow\n" : "");
        template.add("prefix", prefix);

        template.add("instanCounter", instanCounter);
        template.add("instanBase", instanBase);
        template.add("instanAcc", instanAcc);
        template.add("funcCall", funcCall);
        template.add("assignIfZero", assignIfZero);
        template.add("assignIfNegative", assignIfNegative);
        template.add("delCounter", new BlankST(prefix + "scoreboard objectives remove " + counterName + "\n", "", false).getOutput());
        template.add("delBase", new BlankST(prefix + "scoreboard objectives remove " + baseName + "\n", "", false).getOutput());

        return new ArithmeticExprST(template);
    }

    public static ArithmeticExprST createPowFuncTemplate(String expr2, String acc, String tempExpr, String baseName, String loopID, String counterName , String prefix, boolean setComment) {
        ST template = new ST("<Comment><prefix><updateAcc><saveAcc><incrementCounter><funcCall>\n");

        String updateAcc = new ArithmeticExprST(acc, baseName, "*", tempExpr ,Type._num, Type._num , prefix, false).getOutput();
        String saveAcc = new AssignST(acc, tempExpr, Type._num, prefix, false).getOutput();
        String incrementCounter = new BlankST(prefix + "execute as @s run scoreboard players add @s " + counterName + " 1\n" , "", false).getOutput();
        String funcCall = FuncCallST.generateFuncCallToNonMC(loopID, prefix + "execute as @s unless score @s " + counterName + " >= @s " + expr2 + " run ", false).getOutput();

        template.add("Comment", setComment ? "# Pow recc func\n" : "");
        template.add("prefix", prefix);

        template.add("updateAcc", updateAcc);
        template.add("saveAcc", saveAcc);
        template.add("incrementCounter", incrementCounter);
        template.add("funcCall", funcCall );

        return new ArithmeticExprST(template);
    }

    private String getTemplateName(){
        return this.getClass().toString().substring(6); //substring to remove "class "
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
