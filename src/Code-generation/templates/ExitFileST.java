
import org.stringtemplate.v4.ST;

public class ExitFileST implements Template {
    private String output;

    public ExitFileST(boolean setComment){
        ST st = new ST(setComment ? "#exit file\n" : "");
        output = st.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
