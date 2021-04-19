
import org.stringtemplate.v4.ST;

public class DclST implements Template {
    private String output;

    public DclST(String varName, Type type){
        ST st;

        if (type == Type._vector2) {
            st = new ST("<DclX><DclY>");
            st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

            DclST DclX = new DclST(varName + "_x", Type._num);
            DclST DclY = new DclST(varName + "_y", Type._num);

            st.add("DclX", DclX.getOutput());
            st.add("DclY", DclY.getOutput());
        }
        else if (type == Type._vector3) {
            st = new ST("<DclX><DclY><DclZ>");
            st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

            DclST DclX = new DclST(varName + "_x", Type._num);
            DclST DclY = new DclST(varName + "_y", Type._num);
            DclST DclZ = new DclST(varName + "_Z", Type._num);

            st.add("DclX", DclX.getOutput());
            st.add("DclY", DclY.getOutput());
            st.add("DclZ", DclZ.getOutput());
        }
        else {
            st = new ST( "scoreboard objectives add <varName> dummy #<Comment>\n");

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
