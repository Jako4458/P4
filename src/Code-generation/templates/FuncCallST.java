
import org.stringtemplate.v4.ST;

public class FuncCallST implements Template{
    private String output;

    public FuncCallST(String funcName, String call){
        ST st = new ST("#Call <funcName> - <Comment>\n<call>\n");

        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
        st.add("funcName", funcName);
        st.add("call", call);

        output = st.render();
    }

    public FuncCallST(ST template) {
        this.output = template.render();
    }

    public static FuncCallST generateFuncCallToNonMC(String name) {
        ST template = new ST("#Call <name> - <Comment>\nexecute as @s run function <folder>:<name>\n");
        template.add("name", name);
        template.add("folder", "bin");

        return new FuncCallST(template);
    }

    public static FuncCallST generateFuncCallToMC(String name, String folderName) {
        ST template = new ST("#Call <name> - <Comment>\nexecute as @s run function <folder>:<name>\n");
        template.add("name", name);
        template.add("folder", folderName);

        return new FuncCallST(template);
    }

    @Override
    public String getOutput() {
        return output;
    }
}
