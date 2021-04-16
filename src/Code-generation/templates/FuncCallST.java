
import org.stringtemplate.v4.ST;

public class MCStatementST implements Template{
    private String output;

    public MCStatementST(String command){
        ST st = new ST("<Command> #<Comment> \n");
        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("Command", command);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
