package templates;

import org.stringtemplate.v4.ST;

public class MCStatementST {
    public String output;

    public MCStatementST(String command){
        ST st = new ST("<Command> //<Comment>");
        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("Command", command);

        output = st.render();
    }


}
