package templates;

import org.stringtemplate.v4.ST;

public class IfST {
    public String output;

    public IfST(String expr, String stmnt){
        ST st = new ST("execute if <expr> <stmnt> #<Comment> \n");
        st.add("Comment", this.getClass().toString().substring(6));  //substring to remove "class "

        st.add("expr", expr);
        st.add("stmnt", stmnt);

        output = st.render();
    }


}
