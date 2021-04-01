import org.antlr.v4.runtime.ParserRuleContext;

public class SimpleEntry implements SymEntry {
    private final Type type;
    private final String name;
    private final ParserRuleContext ctx;
    private final int modifier;

    public SimpleEntry(String id, Type type, ParserRuleContext ctx, int modifier) {
        this.name = id;
        this.type = type;
        this.ctx = ctx;
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public int getModifier() {
        return this.modifier;
    }

    public ParserRuleContext getCtx() {
        return this.ctx;
    }
}
