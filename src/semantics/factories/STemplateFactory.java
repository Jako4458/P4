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
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2) {
        return createArithmeticExprST(expr1Name, expr2Name, operator, type1, type2, "");
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, getNewExprCounterString(), type1, type2, prefix);
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator) {
        return createArithmeticExprST(expr1Name, expr2, operator, "");
    }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2, operator, prefix, getNewExprCounterString());
    }

    // EqualityExprST
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type) {
        return createEqualityExprST(a, b, operator, type, "");
    }
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type, String prefix) {
        return new EqualityExprST(a, b, operator, getNewExprCounterString(), type, prefix);
    }

    // LogicalExprST
    public LogicalExprST createLogicalExprST(String a, String b, String operator) {
        return createLogicalExprST(a, b, operator, "");
    }
    public LogicalExprST createLogicalExprST(String a, String b, String operator, String prefix) {
        return new LogicalExprST(a, b, operator, prefix, getNewExprCounterString(), generateValidUUID());
    }

    // NegationExprST
    public NegationExprST createNegationExprST(String a, String operator, Type type) {
        return createNegationExprST(a, operator, "", type);
    }
    public NegationExprST createNegationExprST(String a, String operator, String prefix, Type type) {
        return new NegationExprST(a, operator, prefix, getNewExprCounterString(), type);
    }

    // RelationExprST
    public RelationExprST createRelationExprST(String a, String b, String operator) {
        return createRelationExprST(a, b, operator, "");
    }
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
    public MCStatementST createMCStatementST(String command) {
        return new MCStatementST(command);
    }

    // ParameterDependantStmntST
    public ParameterDependantStmntST createParamDependantStmntST(ParseTree ctx){
        return new ParameterDependantStmntST(ctx);
    }

    // DclST
    public DclST createDclST(String varName, Type type) {
        return new DclST(varName, type);
    }


    // InstantST
    public InstanST createInstanST(String varName, Type type) {
        return createInstanST(varName, type, "");
    }

    // InstantST
    public InstanST createInstanST(String varName, Type type, String prefix) {
        return new InstanST(getNewExprCounterString(), varName, type, prefix);
    }

    // InstantST
    public InstanST createInstanST(String varName, String exprName) {
        return new InstanST(varName, exprName);
    }

    public InstanST createInstanST(String varName, int varVal) {
        return new InstanST(varName, varVal);
    }

    // InstantST
    public InstanST createInstanST(SymEntry entry, Type type) {
        return new InstanST(entry.getVarName(), getExprCounterString(), type, "");
    }

    public AssignST createAssignST(String varName, String exprName){
        return new AssignST(varName, exprName);
    }

    public AssignST createAssignST(String varName, int val){
        return new AssignST(varName, val);
    }

    public AssignST createAssignST(String varName, Vector2 val){
        return new AssignST(varName, val);
    }

    public AssignST createAssignST(String varName, Vector3 val){
        return new AssignST(varName, val);
    }

    public String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "_").substring(0,11);}
}
