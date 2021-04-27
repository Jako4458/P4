
import org.stringtemplate.v4.ST;

public class AssignST implements Template {
    private String output;

    public AssignST(String varName, String exprName, Type type, String prefix) {
        ST st;
        if (type == Type._block) {
            st = new ST("#<Comment>\n" +
                    "<prefix>execute as @e[tag=<varName>] at @e[tag=<varName>] run setblock ~ ~-1 ~ <block>\n"
            );

            st.add("block", exprName.substring(1));
            st.add("varName", varName);
        }
        else {
            st = new ST("#<Comment>\n" +
                    "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

            st.add("varName", varName);
            st.add("exprName", exprName);
        }

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

    public AssignST(String varName, Vector2 vec2, String prefix){
        ST st = new ST("<AssignX><AssignY>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        AssignST AssignX = new AssignST(varName + "_x", vec2.getX(), prefix);
        AssignST AssignY = new AssignST(varName + "_y", vec2.getY(), prefix);

        st.add("AssignX", AssignX.getOutput());
        st.add("AssignY", AssignY.getOutput());

        output = st.render();
    }

    public AssignST(String varName, Vector3 vec3, String prefix){
        ST st = new ST("<AssignX><AssignY><AssignZ>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        AssignST AssignX = new AssignST(varName + "_x", vec3.getX(), prefix);
        AssignST AssignY = new AssignST(varName + "_y", vec3.getY(), prefix);
        AssignST AssignZ = new AssignST(varName + "_z", vec3.getZ(), prefix);

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
