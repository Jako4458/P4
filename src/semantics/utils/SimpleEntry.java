import org.antlr.v4.runtime.ParserRuleContext;

public class SimpleEntry implements SymEntry {
    private Type type;
    private String name;
    private ParserRuleContext ctx;

    public SimpleEntry(String id, Type type, ParserRuleContext ctx) {
        this.name = id;
        this.type = type;
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public ParserRuleContext getCtx() {
        return ctx;
    }
}
