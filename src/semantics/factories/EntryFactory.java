import java.util.List;

public class EntryFactory {
    private boolean isMCFunction = false;


    public SimpleEntry createForAssignEntry(String id) {
        return new SimpleEntry(id, Type._num);
    }

    public SimpleEntry createFromType(String id, Type type) {
        return new SimpleEntry(id, type);
    }

    public FuncEntry createFunctionEntry(String id, Type retType, List<SimpleEntry> paramIDs) {
        FuncEntry func = new FuncEntry(isMCFunction, id, retType, paramIDs);
        this.isMCFunction = false;
        return func;
    }

    public void setMCFunction() {
        this.isMCFunction = true;
    }
}
