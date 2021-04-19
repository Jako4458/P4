
import org.stringtemplate.v4.ST;

public class DclST implements Template {
    private String output;

    public DclST(String varName, Type type){
        ST st;

        if (type == Type._vector2) {
            st = new ST("<DclX><DclY>");

            DclST DclX = new DclST(varName + "_x", Type._num);
            DclST DclY = new DclST(varName + "_y", Type._num);

            st.add("DclX", DclX.getOutput());
            st.add("DclY", DclY.getOutput());
        }
        else if (type == Type._vector3) {
            st = new ST("<DclX><DclY><DclZ>");

            DclST DclX = new DclST(varName + "_x", Type._num);
            DclST DclY = new DclST(varName + "_y", Type._num);
            DclST DclZ = new DclST(varName + "_z", Type._num);

            st.add("DclX", DclX.getOutput());
            st.add("DclY", DclY.getOutput());
            st.add("DclZ", DclZ.getOutput());
        }
        else {
            st = new ST(    "#<Comment>\nscoreboard objectives add <varName> dummy \n" +
                                    "scoreboard players set @s <varName> 0\n");

            st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

            st.add("varName", varName);
        }

        output = st.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
