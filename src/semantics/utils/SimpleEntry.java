import org.antlr.v4.runtime.ParserRuleContext;

import java.util.UUID;

public class SimpleEntry implements SymEntry {
    private final Type type;
    private String name;
    private String varName;
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
    public void setName(String newName) {
        this.name = newName;
    }

    public String getVarName(boolean readableNames) {
        if (varName == null) {
            varName = readableNames ? (this.name + "_" + this.toString().replace("@", ""))
                    : UUID.randomUUID().toString().toLowerCase().replace("-", "");

            varName = varName.length() > 14 ? varName.substring(0, 14) : varName;
        }
        return varName ;
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
