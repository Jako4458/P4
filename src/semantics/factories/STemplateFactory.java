import org.antlr.v4.runtime.tree.ParseTree;

import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    private Integer vecExprCounter = 0;
    public String factor1UUID = generateValidUUID();
    public String factor2UUID = generateValidUUID();
//    public String vecFactor1UUID = generateValidUUID();
//    public String vecFactor2UUID = generateValidUUID();

    private Integer newExprCounter() {return ++exprCounter; }

    private String getNewExprCounterString() {return "expr_" + newExprCounter(); }
    public String getExprCounterString() {return "expr_" + exprCounter; }


    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, getNewExprCounterString(), type1, type2, prefix);
    }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2, operator, prefix, getNewExprCounterString());
    }

    // EqualityExprST
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type, String prefix) {
        return new EqualityExprST(a, b, operator, getNewExprCounterString(), type, prefix);
    }

    // LogicalExprST
    public LogicalExprST createLogicalExprST(String a, String b, String operator, String prefix) {
        return new LogicalExprST(a, b, operator, prefix, getNewExprCounterString(), generateValidUUID());
    }

    // NegationExprST
    public NegationExprST createNegationExprST(String a, String operator, String prefix, Type type) {
        return new NegationExprST(a, operator, prefix, getNewExprCounterString(), type);
    }

    // RelationExprST
    public RelationExprST createRelationExprST(String a, String b, String operator, String prefix) {
        return new RelationExprST(a, b, operator, prefix, getNewExprCounterString());
    }


    // MCFuncCallST
    public MCFuncCallST createMCFuncCallST(FuncEntry entry) {
        return new MCFuncCallST(entry.getName());
    }

    // FuncCallST
    public FuncCallST createFuncCallST(FuncEntry entry) {

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

    // MCStatementsST
    public MCStatementST createMCStatementST(String command, String prefix) {
        return new MCStatementST(command, prefix);
    }

    // ParameterDependantStmntST
    public ParameterDependantStmntST createParamDependantStmntST(ParseTree ctx){
        return new ParameterDependantStmntST(ctx);
    }

    // DclST
    public DclST createDclST(String varName, Type type, String prefix) {
        return new DclST(varName, type, prefix);
    }


    // InstantST
    public InstanST createInstanST(String varName, Type type, String prefix) {
        return new InstanST(getNewExprCounterString(), varName, type, prefix);
    }

    // InstantST
    public InstanST createInstanST(String varName, String exprName, String prefix) {
        return new InstanST(varName, exprName, prefix);
    }

    public InstanST createInstanST(String varName, int varVal, String prefix) {
        return new InstanST(varName, varVal, prefix);
    }

    // InstantST
    public InstanST createInstanST(SymEntry entry, Type type, String prefix) {
        return new InstanST(entry.getVarName(), getExprCounterString(), type, prefix);
    }

    public AssignST createAssignST(String varName, String exprName, String prefix){
        return new AssignST(varName, exprName, prefix);
    }

    public AssignST createAssignST(String varName, int val, String prefix){
        return new AssignST(varName, val, prefix);
    }

    public AssignST createAssignST(String varName, Vector2 val, String prefix){
        return new AssignST(varName, val, prefix);
    }

    public AssignST createAssignST(String varName, Vector3 val, String prefix){
        return new AssignST(varName, val, prefix);
    }

    public String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "_").substring(0,11);}
}
