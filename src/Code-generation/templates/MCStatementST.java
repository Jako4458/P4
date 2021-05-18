
import org.stringtemplate.v4.ST;

public class MCStatementST implements Template{
    private String output;

    public MCStatementST(String command, String prefix, boolean setComment){
        ST template = new ST("<Comment><prefix><Command>\n");


        template.add("Comment", setComment ? "#" + this.getTemplateName() + "\n" : "");

        template.add("prefix", prefix);
        template.add("Command", command);

        output = template.render();
    }

    private String getTemplateName(){
        return this.getClass().toString().substring(6); //substring to remove "class "
    }

    @Override
    public String getOutput() {
        return output;
    }
}
