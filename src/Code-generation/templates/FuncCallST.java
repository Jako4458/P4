
import org.stringtemplate.v4.ST;

public class FuncCallST implements Template{
    private String output;

//    public FuncCallST(String funcName, String call){
//        ST st = new ST("#Call <funcName> - <Comment>\n<call>\n");
//
//        st.add("Comment", this.getClass().toString().substring(6)); //substring to remove "class "
//        st.add("funcName", funcName);
//        st.add("call", call);
//
//        output = st.render();
//    }

    public FuncCallST(ST template) {
        this.output = template.render();
    }

    public static FuncCallST generateFuncCallToNonMC(String name, String prefix, boolean setComment) {
        ST template = new ST("<Comment><prefix>execute as @s run function <folder>:<name>\n");

        template.add("Comment", setComment ? "#Call " + name + " - FuncCall\n" : "");

        template.add("name", name);
        template.add("folder", "bin");
        template.add("prefix", prefix);

        return new FuncCallST(template);
    }

    public static FuncCallST generateFuncCallToMC(String name, String folderName, String prefix, boolean setComment) {
        ST template = new ST("<Comment><prefix>execute as @s run function <folder>:<name>\n");

        template.add("Comment", setComment ? "#Call " + name + " - FuncCall\n" : "");
        template.add("name", name);
        template.add("folder", folderName);
        template.add("prefix", prefix);

        return new FuncCallST(template);
    }

    @Override
    public String getOutput() {
        return output;
    }
}
