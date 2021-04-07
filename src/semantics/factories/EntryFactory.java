import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class EntryFactory {
    private boolean isMCFunction = false;

    public SymEntry createFromType(String id, Type type, ParserRuleContext ctx, int modifier) {
        if (type instanceof ArrayType)
            return new ArrayEntry(id, (ArrayType)type, ctx, modifier, 0);
        return new SimpleEntry(id, type, ctx, modifier);
    }

    public FuncEntry createFunctionEntry(String id, Type retType, List<SymEntry> paramIDs, MinespeakParser.FuncSignatureContext ctx) {
        FuncEntry func = new FuncEntry(isMCFunction, id, retType, paramIDs, ctx);
        this.isMCFunction = false;
        return func;
    }

    public void setMCFunction() {
        this.isMCFunction = true;
    }

    public void resetMCFunction() {
        this.isMCFunction = false;
    }
}
