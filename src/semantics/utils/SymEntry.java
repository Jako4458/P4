import org.antlr.v4.runtime.ParserRuleContext;

public interface SymEntry {
    String getName();
    void setName(String newName);
    String getVarName(boolean readableNames);

    Type getType();

    ParserRuleContext getCtx();

    int getModifier();
}
