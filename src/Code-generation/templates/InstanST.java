
import org.stringtemplate.v4.ST;

public class InstanST implements Template {
    private String output;

    public InstanST(String varName, String exprName){
        ST st = new ST( "#<Comment>\nscoreboard objectives add <varName> dummy \n" +
                                "scoreboard players set @s <varName> 0\n" +
                                "execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprName", exprName);

        output = st.render();
    }
    public InstanST(String varName, int exprVal){
        ST st = new ST( "#<Comment>\nscoreboard objectives add <varName> dummy \n" +
                                "scoreboard players set @s <varName><exprVal> \n");

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("exprVal", exprVal);

        output = st.render();
    }

    public InstanST(String varName, Vector2Value vec2){
        ST st = new ST("<InstanX><InstanY>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec2.getValue().getX());
        InstanST InstanY = new InstanST(varName + "_y", vec2.getValue().getY());

        st.add("InstanX", InstanX.getOutput());
        st.add("InstanY", InstanY.getOutput());

        output = st.render();
    }

    public InstanST(String varName, Vector3Value vec3){
        ST st = new ST("<InstanX><InstanY><InstanZ>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec3.getValue().getX());
        InstanST InstanY = new InstanST(varName + "_y", vec3.getValue().getY());
        InstanST InstanZ = new InstanST(varName + "_z", vec3.getValue().getZ());

        st.add("InstanX", InstanX.getOutput());
        st.add("InstanY", InstanY.getOutput());
        st.add("InstanZ", InstanZ.getOutput());
        output = st.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
