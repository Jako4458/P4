import java.util.List;

public class FuncEntry implements SymEntry {
    private boolean isMCFunction;
    private String name;
    private Type retType;
    private List<SimpleEntry> params;

    public FuncEntry(boolean isMCFunction, String name, Type retType, List<SimpleEntry> params) {
        this.isMCFunction = isMCFunction;
        this.name = name;
        this.retType = retType;
        this.params = params;
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

    public List<SimpleEntry> getParams() {
        return this.params;
    }
}
