import org.antlr.v4.runtime.ParserRuleContext;

public interface SymEntry {
    String getName();

    Type getType();

    ParserRuleContext getCtx();

    int getModifier();
}
