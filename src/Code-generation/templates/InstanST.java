
import org.stringtemplate.v4.ST;

public class InstanST implements Template {
    private String output;
    
    public InstanST(String varName, String exprName, String prefix){
        this(varName, exprName, Type._string, prefix);
    }

    public InstanST(String varName, String exprName, Type type, String prefix) {
        if (type == Type._vector2 || type == Type._vector3)
            this.output =  createVectorInstant(varName, exprName, type, prefix).render();
        else if (type == Type._block)
            return;
        else {
            ST st = new ST( "#<Comment>\n<prefix>scoreboard objectives add <varName> dummy \n" +
                    "<prefix>execute at @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

            st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

            st.add("varName", varName);
            st.add("exprName", exprName);
            st.add("prefix", prefix);

            output = st.render();
        }
    }

    public InstanST(String varName, BlockValue blockValue, Vector3 pos,Type type, String prefix) {
        if (type != Type._block)
            return;

        ST st = new ST( "#<Comment>\n<prefix>setblock <posX> <posY> <posZ> <block>\n" +
                "<prefix>summon armor_stand <posX> <posStandY> <posZ> {Tags:[\"<varName>\", \"variable\"],NoGravity:1}"
                );
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("prefix", prefix);
        st.add("varName", varName);
        st.add("block", blockValue.getValue().substring(1).toLowerCase());

        st.add("posX", pos.getX());
        st.add("posY", pos.getY());
        st.add("posZ", pos.getZ());

        st.add("posStandY", pos.getY()+1);

        output = st.render();
    }


        private ST createVectorInstant(String varName, String exprName, Type type, String prefix) {
        ST template;
        if (type == Type._vector2)
            template = new ST("<X><Y>");
        else
            template = new ST("<X><Y><Z>");

        InstanST X = new InstanST(varName + "_x", exprName + "_x", Type._num, prefix);
        InstanST Y = new InstanST(varName + "_y", exprName + "_y", Type._num, prefix);

        template.add("X", X.getOutput());
        template.add("Y", Y.getOutput());

        if (type == Type._vector3){
            InstanST Z = new InstanST(varName + "_z", exprName + "_z", Type._num, prefix);
            template.add("Z", Z.getOutput());
        }
        return template;
    }

    public InstanST(String varName, int exprVal, String prefix) {
        ST st = new ST( "#<Comment>\n<prefix>scoreboard objectives add <varName> dummy \n" +
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