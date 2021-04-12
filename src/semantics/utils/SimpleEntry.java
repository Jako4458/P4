import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Vector;

public class SimpleEntry implements SymEntry {
    private final Type type;
    private final String name;
    private final ParserRuleContext ctx;
    private final int modifier;
    private Object value;

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

    public void setValue(int value) {
        if (this.type == Type._num)
            this.value = value;
    }

    public void setValue(boolean value) {
        if (this.type == Type._bool)
            this.value = value;
    }

    public void setValue(Vector value) {
        if (this.type == Type._vector2 || this.type == Type._vector3)
            this.value = value;
    }

    @Override
    public void setValue(String valueString) {

        if (this.type == Type._string || this.type == Type._block)
            this.value = valueString;
        else if (this.type == Type._num)
            this.value = Integer.parseInt(valueString);
        else if (this.type == Type._bool)
            this.value = Boolean.parseBoolean(valueString);
        else if (this.type == Type._vector2 || this.type == Type._vector3){
            valueString = valueString.replace("[", "");
            valueString = valueString.replace("]", "");
            valueString = valueString.replace(" ", "");
            String[] valuesStrings = valueString.split(",");

            this.value = new Vector<Integer>();
            for (String val:valuesStrings) {
                ((Vector)this.value).add(Integer.parseInt(val));
            }
        }

        return;
    }

    @Override
    public Object getValue() {
        return value;
    }


    public ParserRuleContext getCtx() {
        return this.ctx;
    }
}
