import org.stringtemplate.v4.ST;

public class InstanST {
    public String output;

    public InstanST(Value val){
        ST st = new ST("scoreboard objectives add <varName> dummy \nscoreboard <varName> add <value> //<Comment>");
        st.add("Comment", this.getClass().toString().substring(16));

        st.add("varName", val.);
        st.add("value", value);

        output = st.render();
    }


}
