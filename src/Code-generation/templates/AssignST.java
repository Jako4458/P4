
import org.stringtemplate.v4.ST;

public class AssignST implements Template {
    private String output;

    public AssignST(String varName, String exprName){
        ST st = new ST( "#<Comment>\n" +
                                "execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprName", exprName);

        output = st.render();
    }
    public AssignST(String varName, int exprVal){
        ST st = new ST( "#<Comment>\n" +
                                "scoreboard players set @s <varName> <exprVal> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprVal", exprVal);

        output = st.render();
    }

    public AssignST(String varName, Vector2 vec2){
        ST st = new ST("<AssignX><AssignY>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        AssignST AssignX = new AssignST(varName + "_x", vec2.getX());
        AssignST AssignY = new AssignST(varName + "_y", vec2.getY());

        st.add("AssignX", AssignX.getOutput());
        st.add("AssignY", AssignY.getOutput());

        output = st.render();
    }

    public AssignST(String varName, Vector3 vec3){
        ST st = new ST("<AssignX><AssignY><AssignZ>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        AssignST AssignX = new AssignST(varName + "_x", vec3.getX());
        AssignST AssignY = new AssignST(varName + "_y", vec3.getY());
        AssignST AssignZ = new AssignST(varName + "_z", vec3.getZ());

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
