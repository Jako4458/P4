
import org.stringtemplate.v4.ST;

public class InstanST implements Template {
    private String output;

    public InstanST(String varName, String value){
        ST st = new ST("scoreboard objectives add <varName> dummy \nscoreboard players set @s <varName> <value> #<Comment>\n");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("varName", varName);
        st.add("value", value);

        output = st.render();
    }

    public InstanST(String varName, Vector2Value vec2){
        ST st = new ST("<InstanX><InstanY>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec2.getValue().getX().toString());
        InstanST InstanY = new InstanST(varName + "_y", vec2.getValue().getY().toString());

        st.add("InstanX", InstanX.getOutput());
        st.add("InstanY", InstanY.getOutput());

        output = st.render();
    }

    public InstanST(String varName, Vector3Value vec3){
        ST st = new ST("<InstanX><InstanY><InstanZ>");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        InstanST InstanX = new InstanST(varName + "_x", vec3.getValue().getX().toString());
        InstanST InstanY = new InstanST(varName + "_y", vec3.getValue().getY().toString());
        InstanST InstanZ = new InstanST(varName + "_z", vec3.getValue().getZ().toString());

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
