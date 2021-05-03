
import org.stringtemplate.v4.ST;

import java.util.ArrayList;

public class WhileST implements Template {
    private final String output;

    public final ArrayList<Template> templates;

    public WhileST(ArrayList<Template> templates){
        ST st = new ST("#While");

        output = st.render();

        this.templates = templates;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
