
import org.stringtemplate.v4.ST;

public class BlankST implements Template {
    private String output;

    public BlankST(String string, String comment, boolean setComment){
        ST template = new ST("<Comment><string>\n");

        template.add("Comment", setComment ? "#" + comment +"\n" : "");
        template.add("string", string);

        output = template.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
