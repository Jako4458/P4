package templates;

import org.stringtemplate.v4.ST;

public class IfElseST {
    public String output;

    public IfElseST(String expr, String stmnt, String elseStmnt){
        ST st = new ST("//<Comment>\nexecute if <expr> <stmnt> \n execute unless <expr> <elseStmnt>");
        st.add("Comment", this.getClass().toString().substring(16));

        st.add("expr", expr);
        st.add("stmnt", stmnt);
        st.add("elseStmnt", elseStmnt);

        output = st.render();
    }


}
