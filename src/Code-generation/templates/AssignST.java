
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
                        "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName>\n");
                break;
            case Type.VECTOR2:
                template = new ST("<Comment>" +
                        "<prefix>execute at @s store result score @s <varName>_x run scoreboard players get @s <exprName>_x\n" +
                        "<prefix>execute at @s store result score @s <varName>_y run scoreboard players get @s <exprName>_y\n");
                break;
            case Type.VECTOR3:
                template = new ST("<Comment>" +
                        "<prefix>execute at @s store result score @s <varName>_x run scoreboard players get @s <exprName>_x\n" +
                        "<prefix>execute at @s store result score @s <varName>_y run scoreboard players get @s <exprName>_y\n" +
                        "<prefix>execute at @s store result score @s <varName>_z run scoreboard players get @s <exprName>_z\n");
                break;
            case Type.STRING:
            default:
                throw new RuntimeException("assignST");
        }

        template.add("varName", varName);
        template.add("exprName", exprName);

        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);

        output = template.render();
    }

    public AssignST(String varName, int exprVal, String prefix, boolean setComment){
        ST st = new ST( "<Comment>" +
                                "<prefix>scoreboard players set @s <varName> <exprVal> \n");

        st.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "

        st.add("prefix", prefix);
        st.add("varName", varName);
        st.add("exprVal", exprVal);

        output = st.render();
    }

    public AssignST(String varName, Vector3 tempPos, String exprName, String prefix, boolean setComment){
        ST st = new ST( "<Comment>" +
                                "<prefix>execute as @e[tag=<exprName>,limit=1] at @e[tag=<exprName>,limit=1] run clone ~ ~-1 ~ ~ ~-1 ~ <tempX> <tempY> <tempZ>\n" +
                                "<prefix>execute as @e[tag=<varName>,limit=1] at @e[tag=<varName>,limit=1] run clone <tempX> <tempY> <tempZ> <tempX> <tempY> <tempZ> ~ ~-1 ~\n");

        st.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        st.add("prefix", prefix);

        st.add("varName", varName);
        st.add("exprName", exprName);

        st.add("tempX", tempPos.getX());
        st.add("tempY", tempPos.getY());
        st.add("tempZ", tempPos.getZ());

        output = st.render();
    }

    public AssignST(String varName, Vector2 vec2, String prefix, boolean setComment){
        ST st = new ST("<Comment><AssignX><AssignY>");

        AssignST AssignX = new AssignST(varName + "_x", vec2.getX(), prefix, false);
        AssignST AssignY = new AssignST(varName + "_y", vec2.getY(), prefix, false);

        st.add("Comment", setComment ? "#Vector2 assign\n" : ""); //substring to remove "class "

        st.add("AssignX", AssignX.getOutput());
        st.add("AssignY", AssignY.getOutput());

        output = st.render();
    }

    public AssignST(String varName, Vector3 vec3, String prefix, boolean setComment){
        ST st = new ST("<Comment><AssignX><AssignY><AssignZ>");

        AssignST AssignX = new AssignST(varName + "_x", vec3.getX(), prefix, false);
        AssignST AssignY = new AssignST(varName + "_y", vec3.getY(), prefix, false);
        AssignST AssignZ = new AssignST(varName + "_z", vec3.getZ(), prefix, false);

        st.add("Comment", setComment ? "#Vector3 assign\n" : "");

        st.add("AssignX", AssignX.getOutput());
        st.add("AssignY", AssignY.getOutput());
        st.add("AssignZ", AssignZ.getOutput());
        output = st.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
