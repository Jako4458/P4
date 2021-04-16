import org.antlr.v4.runtime.tree.ParseTree;

import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    public String factor1UUID = UUID.randomUUID().toString();
    public String factor2UUID = UUID.randomUUID().toString();

    private Integer getExprCounter() {return ++exprCounter; }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, "", "expr_" + getExprCounter());
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, prefix, "expr_" + getExprCounter());
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator) {
        return createArithmeticExprST(expr1Name, expr2, operator, "");
    }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2, operator, prefix, "expr_" + getExprCounter());
    }

    public ArithmeticExprST createArithmeticExprST (SymEntry entry1, SymEntry entry2, String operator) {
        return createArithmeticExprST(entry1, entry2, operator, "");
    }

    public ArithmeticExprST createArithmeticExprST (SymEntry entry1, SymEntry entry2, String operator, String prefix) {
        if (operator.equals("Pow"))
            return new ArithmeticExprST(entry1.getVarName(), Value.value(entry2.getValue().getCasted(NumValue.class)), operator, prefix, "expr_" + exprCounter);

        return createArithmeticExprST(entry1.getVarName(), entry2.getVarName(), operator, prefix);
    }

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
            return new InstanST(entry.getVarName(), entry.getValue().getCasted(Vector2Value.class));
        if (entry.getType() == Type._vector3)
            return new InstanST(entry.getVarName(), entry.getValue().getCasted(Vector3Value.class));

        return new InstanST(entry.getVarName(), "expr_" + exprCounter.toString());
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
}
