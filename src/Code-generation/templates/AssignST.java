
import org.stringtemplate.v4.ST;

public class AssignST implements Template {
    private String output;

    // Type can never be Type._block
    public AssignST(String varName, String exprName, Type type, String prefix) {
        ST template;
        switch (type.getTypeAsInt()) {
            case Type.NUM:
            case Type.BOOL:
                template = new ST("#<Comment>\n" +
                        "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName>\n");
                break;
            case Type.VECTOR2:
                template = new ST("#<Comment>\n" +
                        "<prefix>execute at @s store result score @s <varName>_x run scoreboard players get @s <exprName>_x\n" +
                        "<prefix>execute at @s store result score @s <varName>_y run scoreboard players get @s <exprName>_y\n");
                break;
            case Type.VECTOR3:
                template = new ST("#<Comment>\n" +
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

        template.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "
        template.add("prefix", prefix);

        output = template.render();
    }

    public AssignST(String varName, int exprVal, String prefix){
        ST st = new ST( "#<Comment>\n" +
                                "<prefix>scoreboard players set @s <varName> <exprVal> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("prefix", prefix);
        st.add("varName", varName);
        st.add("exprVal", exprVal);

        output = st.render();
    }

    public AssignST(String varName, Vector3 tempPos, String exprName, String prefix){
        ST st = new ST( "<Comment>\n" +
                                "<prefix>execute as <exprName> at <exprName> run clone ~ ~-1 ~ ~ ~-1 ~ <tempX> <tempY> <tempZ>\n" +
                                "<prefix>execute as <varName> at <varName> run clone <tempX> <tempY> <tempZ> <tempX> <tempY> <tempZ> ~ ~-1 ~\n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "
        st.add("prefix", prefix);

        st.add("varName", varName);
        st.add("exprName", exprName);

        st.add("tempX", tempPos.getX());
        st.add("tempY", tempPos.getY());
        st.add("tempZ", tempPos.getZ());

        output = st.render();
    }

    public AssignST(String varName, Vector2 vec2, String prefix){
        ST st = new ST("<Comment>\n<AssignX><AssignY>");

        AssignST AssignX = new AssignST(varName + "_x", vec2.getX(), prefix);
        AssignST AssignY = new AssignST(varName + "_y", vec2.getY(), prefix);

        st.add("Comment", "#Vector2 assign");

        st.add("AssignX", AssignX.getOutput());
        st.add("AssignY", AssignY.getOutput());

        output = st.render();
    }

    public AssignST(String varName, Vector3 vec3, String prefix){
        ST st = new ST("<Comment>\n<AssignX><AssignY><AssignZ>");

        AssignST AssignX = new AssignST(varName + "_x", vec3.getX(), prefix);
        AssignST AssignY = new AssignST(varName + "_y", vec3.getY(), prefix);
        AssignST AssignZ = new AssignST(varName + "_z", vec3.getZ(), prefix);

        st.add("Comment", "#Vector3 assign");

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
