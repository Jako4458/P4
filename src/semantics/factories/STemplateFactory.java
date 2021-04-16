import org.antlr.v4.runtime.tree.ParseTree;

public class STemplateFactory {

    public MCFuncCallST CreateMCFuncCallST(FuncEntry entry) {
        return new MCFuncCallST(entry.getName());
    }

    public FuncCallST CreateFuncCallST(FuncEntry entry) {

        var combinedST = entry.getOutput().stream().reduce(
                                                (str1, str2) -> new BlankST(str1.getOutput() + str2.getOutput())
                                            ).get();

        String paramList = " ";

        for (var param:entry.getParams()) {
            paramList += entry.scope.lookup(param.getName()).prettyPrint() + ", ";
        }
        paramList += " ";

        return new FuncCallST(entry.getName() + paramList + entry.toString(), combinedST.getOutput());
    }

    public MCStatementST CreateMCStatementST(String command) {
        return new MCStatementST(command);
    }

    public ParameterDependantStmntST createParamDependantStmntST(ParseTree ctx){
        return new ParameterDependantStmntST(ctx);
    }

    public InstanST createInstanST(SymEntry entry) {
        if (entry.getType() == Type._vector2)
            return new InstanST(getName(entry), entry.getValue().getCasted(Vector2Value.class));
        if (entry.getType() == Type._vector3)
            return new InstanST(getName(entry), entry.getValue().getCasted(Vector3Value.class));

        return new InstanST(getName(entry), getValAsString(entry));
    }

    private String getValAsString(SymEntry entry) {

        if (entry.getType() == Type._num)
            return Value.value(entry.getValue().getCasted(NumValue.class)).toString();
        if (entry.getType() == Type._bool)
            return Value.value(entry.getValue().getCasted(BoolValue.class)) ? "1" : "0" ;
        if (entry.getType() == Type._vector2)
            return Value.value(entry.getValue().getCasted(Vector2Value.class)).toString("~");
        if (entry.getType() == Type._vector3)
            return Value.value(entry.getValue().getCasted(Vector3Value.class)).toString("~");

        return entry.toString();
    }

    private String getName(SymEntry entry) {return entry.getName() + "_" + entry.toString();}

}
