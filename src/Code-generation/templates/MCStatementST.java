
import org.stringtemplate.v4.ST;

public class MCStatementST implements Template{
    private String output;

    public MCStatementST(String command, String prefix, boolean setComment){
        ST st = new ST("<Comment><prefix><Command>\n");


        st.add("Comment", setComment ? "#" + this.getClass().toString().substring(6) + "\n" : ""); //substring to remove "class "

        st.add("prefix", prefix);
        st.add("Command", command);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
