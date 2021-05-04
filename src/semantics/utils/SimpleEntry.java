import org.antlr.v4.runtime.ParserRuleContext;

import java.util.UUID;

public class SimpleEntry implements SymEntry {
    private final Type type;
    private String name;
    private String varName;
    private final ParserRuleContext ctx;
    private final int modifier;
    private Value value;

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

            varName = varName.length() > 16 ? varName.substring(0, 16) : varName;
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

    public void setValue(Value value) {
        this.value = value;
    }

    public void setValue(int value) {
        if (this.type == Type._num)
            this.value = new NumValue(value, Type._num);
    }

    public void setValue(Boolean value) {
        if (this.type == Type._bool)
            this.value = new BoolValue(value, Type._bool);
    }

    public void setValue(Vector2 value) {
        if (this.type == Type._vector2)
            this.value = new Vector2Value(value, Type._vector2);
    }

    public void setValue(Vector3 value) {
        if (this.type == Type._vector3)
            this.value = new Vector3Value(value, Type._vector3);
    }

    @Override
    public void setValue(String valueString) {

        if (this.type == Type._string)
            this.value = new StringValue(valueString, Type._string);
        if (this.type == Type._block)
            this.value = new BlockValue(valueString, Type._block);
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public String prettyPrint() {
        return this.getName() + ":" + this.value.toString();
    }


    public ParserRuleContext getCtx() {
        return this.ctx;
    }
}
