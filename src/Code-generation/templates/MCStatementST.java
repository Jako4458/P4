
import org.stringtemplate.v4.ST;

public class MCStatementST implements Template{
    private String output;

    public MCStatementST(String command, String prefix, boolean setComment){
        ST template = new ST("<Comment><prefix><Command>\n");


        template.add("Comment", setComment ? "#" + this.getClass().toString().substring(6) + "\n" : ""); //substring to remove "class "

        template.add("prefix", prefix);
        template.add("Command", command);

        output = template.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
