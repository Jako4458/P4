import java.util.List;

public class FuncEntry implements SymEntry {
    private final boolean isMCFunction;
    private final String name;
    private final Type retType;
    private final List<SymEntry> params;
    private final MinespeakParser.FuncSignatureContext ctx;
    private String value;

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
    public Type getType() {
        return this.retType;
    }

    @Override
    public int getModifier() {
        return MinespeakParser.CONST;
    }

    @Override
    public void setValue(int value) {

    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
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
