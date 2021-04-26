
import org.stringtemplate.v4.ST;

public class DclST implements Template {
    private String output;

    public DclST(String varName, Type type, String prefix){
        ST st;

        if (type == Type._block){
            return;
        }
        else if (type == Type._vector2 || type == Type._vector3) {
            if (type == Type._vector2)
                st = new ST("<DclX><DclY>");
            else
                st = new ST("<DclX><DclY><DclZ>");

            DclST DclX = new DclST(varName + "_x", Type._num, prefix);
            DclST DclY = new DclST(varName + "_y", Type._num, prefix);

            st.add("DclX", DclX.getOutput());
            st.add("DclY", DclY.getOutput());

            if (type == Type._vector3) {
                DclST DclZ = new DclST(varName + "_z", Type._num, prefix);
                st.add("DclZ", DclZ.getOutput());
            }
        }
        else {
            st = new ST(    "#<Comment>\n<prefix>scoreboard objectives add <varName> dummy \n" +
                                    "<prefix>scoreboard players set @s <varName> 0\n");

            st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

            st.add("prefix", prefix);
            st.add("varName", varName);
        }

        output = st.render();
    }

    public DclST (String varName, Vector3 pos, Type type, String prefix) {
        if (type != Type._block)
            return;

        ST st = new ST( "#<Comment>\n " +
                "<prefix>summon armor_stand <posX> <posStandY> <posZ> {Tags:[\"<varName>\", \"variable\"],NoGravity:1}"
        );

        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "
        st.add("prefix", prefix);

        st.add("varName", varName);
        st.add("posX", pos.getX());
        st.add("posStandY", pos.getY()+1);
        st.add("posZ", pos.getZ());

        output = st.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
