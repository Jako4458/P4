import java.util.ArrayList;
import java.util.List;

public class FuncEntry implements SymEntry {
    private final boolean isMCFunction;
    private String name;
    private final Type retType;
    private final List<SymEntry> params;
    private final MinespeakParser.FuncSignatureContext ctx;
    public Scope scope;
    private Value value;
    public SymEntry retVal;

    private ArrayList<Template> output = new ArrayList<>();

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

    public void addTemplate(Template template) {
        this.output.add(template);
    }

    public ArrayList<Template> getOutput() {
        return this.output;
    }

    public void setOutput(ArrayList<Template> newOutput) {
        this.output = newOutput;
    }

    @Override
    public void setValue(int value) {    }

    @Override
    public void setValue(Value value) {
        this.value = value;
    }

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

    @Override
    public String prettyPrint() {
        return null;
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
