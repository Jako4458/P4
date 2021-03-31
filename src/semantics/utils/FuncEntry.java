import java.util.List;

public class FuncEntry implements SymEntry {
    private boolean isMCFunction;
    private String name;
    private Type retType;
    private List<SimpleEntry> params;
    private MinespeakParser.FuncSignatureContext ctx;

    public FuncEntry(boolean isMCFunction, String name, Type retType, List<SimpleEntry> params, MinespeakParser.FuncSignatureContext ctx) {
        this.isMCFunction = isMCFunction;
        this.name = name;
        this.retType = retType;
        this.params = params;
        this.ctx = ctx;
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

    public MinespeakParser.FuncSignatureContext getCtx() {
        return ctx;
    }
}
