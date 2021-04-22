
import org.stringtemplate.v4.ST;

public class MCStatementST implements Template{
    private String output;

    public MCStatementST(String command, String prefix){
        ST st = new ST("#<Comment> \n<prefix><Command>\n");
        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("prefix", prefix);
        st.add("Command", command);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
