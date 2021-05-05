
import org.stringtemplate.v4.ST;

public class MCFuncCallST implements Template{
    private String output;

    public MCFuncCallST(String funcName, boolean setComment){
        ST st = new ST("<Comment>execute run <funcName>");

        st.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        st.add("funcName", funcName);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
