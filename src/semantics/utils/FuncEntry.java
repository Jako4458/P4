import java.util.List;

public class FuncEntry implements SymEntry {
    private final boolean isMCFunction;
    private final String name;
    private final Type retType;
    private final List<SymEntry> params;
    private final MinespeakParser.FuncSignatureContext ctx;
    private Value value;

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
    public void setValue(Boolean value) {
    }

    @Override
    public void setValue(Vector2 value) {
    }

    @Override
    public void setValue(Vector3 value) {
    }

    @Override
    public void setValue(String value) {
        this.value = new StringValue(value, Type._string);
    }

    @Override
    public Value getValue() {
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
