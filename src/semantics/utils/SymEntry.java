import org.antlr.v4.runtime.ParserRuleContext;

public interface SymEntry {
    String getName();
    void setName(String newName);
    String getVarName();

    Type getType();

    ParserRuleContext getCtx();

    int getModifier();

    void setValue(Value value);
    void setValue(int value);
    void setValue(Boolean value);
    void setValue(Vector2 value);
    void setValue(Vector3 value);


    void setValue(String value);

    Value getValue();

    String prettyPrint();
}
