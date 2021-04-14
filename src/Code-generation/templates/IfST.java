package templates;

import org.stringtemplate.v4.ST;

public class IfST {
    public String output;

    public IfST(String expr, String stmnt){
        ST st = new ST("execute if <expr> <stmnt> //<Comment>");
        st.add("Comment", this.getClass().toString().substring(16));

        st.add("expr", expr);
        st.add("stmnt", stmnt);

        output = st.render();
    }


}
