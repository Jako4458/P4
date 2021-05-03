
import org.stringtemplate.v4.ST;

public class EnterNewFileST implements Template {
    private final String output;

    public final String fileName;
    public boolean isMcfunction;

    public EnterNewFileST(String fileName, boolean isMcfunction){
        ST st = new ST("#newfile <FileName>");

        st.add("FileName", fileName);
        output = st.render();

        this.fileName = fileName;
        this.isMcfunction = isMcfunction;
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
