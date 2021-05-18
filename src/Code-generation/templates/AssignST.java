
import exceptions.NotImplementedException;
import org.stringtemplate.v4.ST;

public class AssignST implements Template {
    private String output;

    // Type can never be Type._block
    public AssignST(String varName, String exprName, Type type, String prefix, boolean setComment) {
        ST template;
        switch (type.getTypeAsInt()) {
            case Type.NUM:
            case Type.BOOL:
                template = new ST("<Comment>" +
                        "<prefix>execute as @s store result score @s <varName> run scoreboard players get @s <exprName>\n");
                break;
            case Type.VECTOR2:
            case Type.VECTOR3:
                template = new ST("<Comment>" +
                        "<prefix>execute as @s store result score @s <varName>_x run scoreboard players get @s <exprName>_x\n" +
                        "<prefix>execute as @s store result score @s <varName>_y run scoreboard players get @s <exprName>_y\n" +
                        "<prefix>execute as @s store result score @s <varName>_z run scoreboard players get @s <exprName>_z\n");
                break;
            case Type.STRING:
                throw new NotImplementedException();
            default:
                throw new RuntimeException("assignST");
        }

        template.add("varName", varName);
        template.add("exprName", exprName);

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");
        template.add("prefix", prefix);

        output = template.render();
    }

    public AssignST(String varName, int exprVal, String prefix, boolean setComment){
        ST template = new ST( "<Comment>" +
                                "<prefix>scoreboard players set @s <varName> <exprVal> \n");

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");

        template.add("prefix", prefix);
        template.add("varName", varName);
        template.add("exprVal", exprVal);

        output = template.render();
    }

    public AssignST(String varName, Vector3 tempPos, String exprName, String prefix, boolean setComment){
        ST template = new ST( "<Comment>" +
                                "<prefix>execute as @e[tag=<exprName>,limit=1] at @e[tag=<exprName>,limit=1] run clone ~ ~-1 ~ ~ ~-1 ~ <tempX> <tempY> <tempZ>\n" +
                                "<prefix>execute as @e[tag=<varName>,limit=1] at @e[tag=<varName>,limit=1] run clone <tempX> <tempY> <tempZ> <tempX> <tempY> <tempZ> ~ ~-1 ~\n");

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");
        template.add("prefix", prefix);

        template.add("varName", varName);
        template.add("exprName", exprName);

        template.add("tempX", tempPos.getX());
        template.add("tempY", tempPos.getY());
        template.add("tempZ", tempPos.getZ());

        output = template.render();
    }

    private String getTemplateName(){
        return this.getClass().toString().substring(6); //substring to remove "class "
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
