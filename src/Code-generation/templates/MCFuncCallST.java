
import org.stringtemplate.v4.ST;

public class MCFuncCallST implements Template{
    private String output;

    public MCFuncCallST(String funcName){
        ST st = new ST("#<Comment>\nexecute run <funcName> - ");

        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("funcName", funcName);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
