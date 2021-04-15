import org.antlr.v4.runtime.ParserRuleContext;

public interface SymEntry {
    String getName();

    Type getType();

    ParserRuleContext getCtx();

    int getModifier();

    void setValue(int value);
    void setValue(Boolean value);
    void setValue(Vector2 value);
    void setValue(Vector3 value);


    void setValue(String value);

    Value getValue();
}
