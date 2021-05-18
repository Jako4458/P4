
import org.stringtemplate.v4.ST;

public class MCFuncCallST implements Template{
    private String output;

    public MCFuncCallST(String funcName, boolean setComment){
        ST template = new ST("<Comment>execute run <funcName>");

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");
        template.add("funcName", funcName);

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
