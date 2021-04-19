
import org.stringtemplate.v4.ST;

public class InstanST implements Template {
    private String output;

    public InstanST(String varName, String exprName, String prefix) {
        ST st = new ST( "#<Comment>\n<prefix>scoreboard objectives add <varName> dummy \n" +
                "<prefix>scoreboard players set @s <varName> 0\n" +
                "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprName", exprName);
        st.add("prefix", prefix);

        output = st.render();
    }

    public InstanST(String varName, String exprName){
        this(varName, exprName, "");
    }

    public InstanST(String varName, int exprVal, String prefix) {
        ST st = new ST( "#<Comment>\n<prefix>scoreboard objectives add <varName> dummy \n" +
                "<prefix>scoreboard players set @s <varName> 0\n" +
                "<prefix>scoreboard players set @s <varName> <exprVal> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprVal", exprVal);
        st.add("prefix", prefix);

        output = st.render();
    }

    public InstanST(String varName, int exprVal){
        this(varName, exprVal, "");
    }

    public InstanST(String varName, Vector2Value vec2, String prefix) {
        ST st = new ST("<InstanX><InstanY>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec2.getValue().getX(), prefix);
        InstanST InstanY = new InstanST(varName + "_y", vec2.getValue().getY(), prefix);

        st.add("InstanX", InstanX.getOutput());
        st.add("InstanY", InstanY.getOutput());

        output = st.render();
    }

    public InstanST(String varName, Vector2Value vec2){
        this(varName, vec2, "");
    }

    public InstanST(String varName, Vector3Value vec3, String prefix) {
        ST st = new ST("<InstanX><InstanY><InstanZ>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec3.getValue().getX(), prefix);
        InstanST InstanY = new InstanST(varName + "_y", vec3.getValue().getY(), prefix);
        InstanST InstanZ = new InstanST(varName + "_z", vec3.getValue().getZ(), prefix);

        st.add("InstanX", InstanX.getOutput());
        st.add("InstanY", InstanY.getOutput());
        st.add("InstanZ", InstanZ.getOutput());
        output = st.render();
    }

    public InstanST(String varName, Vector3Value vec3){
        this(varName, vec3, "");
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
