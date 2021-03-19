import java.util.List;

public class FuncEntry implements SymEntry {
    private boolean isMCFunction;
    private String name;
    private Type retType;
    private List<String> params;

    public FuncEntry(boolean isMCFunction, String name, Type retType, List<String> params) {
        this.isMCFunction = isMCFunction;
        this.name = name;
        this.retType = retType;
        this.params = params;
    }

    public FuncEntry(boolean isMCFunction, MinespeakParser.FuncContext ctx) {
        this.isMCFunction = isMCFunction;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.retType;
    }

    public boolean isMCFunction() {
        return this.isMCFunction;
    }

    public List<String> getParams() {
        return this.params;
    }
}
