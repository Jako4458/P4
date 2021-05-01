
import org.stringtemplate.v4.ST;

public class AssignST implements Template {
    private String output;

    public AssignST(String varName, String exprName, Type type, String prefix) {
        ST st = new ST("#<Comment>\n" +
                "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

        st.add("varName", varName);
        st.add("exprName", exprName);

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "
        st.add("prefix", prefix);

        output = st.render();
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
