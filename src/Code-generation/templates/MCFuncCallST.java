
import org.stringtemplate.v4.ST;

public class MCFuncCallST implements Template{
    private String output;

    public MCFuncCallST(String funcName, boolean setComment){
        ST template = new ST("<Comment>execute run <funcName>");

        template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("funcName", funcName);

        output = template.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
