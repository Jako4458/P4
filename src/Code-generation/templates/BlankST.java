
import org.stringtemplate.v4.ST;

public class BlankST implements Template {
    private String output;

    public BlankST(String string, boolean setComment){
        ST template = new ST("#<Comment><string>\n");

        template.add("Comment", setComment ? "#Template\n" : ""); //substring to remove "class "
        template.add("string", string);

        output = template.render();
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
