
import org.stringtemplate.v4.ST;

public class ExitFileST implements Template {
    private String output;

    public ExitFileST(){
        ST st = new ST("#exit file");
        output = st.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
