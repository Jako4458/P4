import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayEntry implements SymEntry {
    private String name;
    private final ArrayType type;
    private final Type baseType;
    private final int size;
    private final ParserRuleContext ctx;
    private final int modifier;

    public ArrayEntry(String name, ArrayType type, ParserRuleContext ctx, int modifier, int size) {
        this.name = name;
        this.type = type;
        this.baseType = this.type.type;
        this.size = size;
        this.ctx = ctx;
        this.modifier = modifier;
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
        return this.type;
    }

    @Override
    public ParserRuleContext getCtx() {
        return this.ctx;
    }

    @Override
    public int getModifier() {
        return this.modifier;
    }

    public int getSize() {
        return this.size;
    }

    public Type getBaseType() {
        return this.baseType;
    }
}
