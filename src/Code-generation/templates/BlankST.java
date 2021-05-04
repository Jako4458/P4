
import org.stringtemplate.v4.ST;

public class BlankST implements Template {
    private String output;

    public BlankST(String string, boolean setComment){
        ST st = new ST("#<Comment><string>\n");

        st.add("Comment", setComment ? "#Template\n" : ""); //substring to remove "class "
        st.add("string", string);

        output = st.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
