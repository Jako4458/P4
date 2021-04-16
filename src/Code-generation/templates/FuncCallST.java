
import org.stringtemplate.v4.ST;

public class FuncCallST implements Template{
    private String output;

    public FuncCallST(String funcName, String call){
        ST st = new ST("#Call <funcName> - <Comment>\n<call>");

        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("funcName", funcName);
        st.add("call", call);

        output = st.render();
    }

    @Override
    public String getOutput() {
        return output;
    }
}
