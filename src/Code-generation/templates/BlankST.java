
import org.stringtemplate.v4.ST;

public class BlankST implements Template {
    private String output;

    public BlankST(String string){
        ST st = new ST("<string>");

        st.add("string", string);

        output = st.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
