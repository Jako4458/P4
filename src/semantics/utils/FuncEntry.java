import java.util.List;

public class FuncEntry implements SymEntry {
    private final boolean isMCFunction;
    private String name;
    private final Type retType;
    private final List<SymEntry> params;
    private final MinespeakParser.FuncSignatureContext ctx;
    public Scope scope;
    public SymEntry retVal;

    public FuncEntry(boolean isMCFunction, String name, Type retType, List<SymEntry> params, MinespeakParser.FuncSignatureContext ctx) {
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
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String getVarName(boolean readableNames) {
        return null;
    }

    @Override
    public Type getType() {
        return this.retType;
    }

    @Override
    public int getModifier() {
        return MinespeakParser.CONST;
    }

    public boolean isMCFunction() {
        return this.isMCFunction;
    }

    public List<SymEntry> getParams() {
        return this.params;
    }

    public MinespeakParser.FuncSignatureContext getCtx() {
        return ctx;
    }
}
