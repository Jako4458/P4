import org.antlr.v4.runtime.tree.ParseTree;

public class ParameterDependantStmntST implements Template{
    private String output;
    public ParseTree context;

    public ParameterDependantStmntST(ParseTree ctx){
        context = ctx;
    }

    @Override
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {this.output = output; }
}
