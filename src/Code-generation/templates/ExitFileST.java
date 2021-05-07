
import org.stringtemplate.v4.ST;

public class ExitFileST implements Template {
    private String output;

    public ExitFileST(boolean setComment){
        ST template = new ST(setComment ? "#exit file\n" : "");
        output = template.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
