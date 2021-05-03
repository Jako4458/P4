import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Locale;
import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    public String factor1UUID = generateValidUUID();
    public String factor2UUID = generateValidUUID();
    public Vector3 blockFactor1Pos = new Vector3(0, 255, 0); // is dependant on dcl before block var uses
    public Vector3 blockFactor2Pos = new Vector3(1, 255, 0); // is dependant on dcl before block var uses
    public String BlockFactor1 = "BlockFactor1";
    public String BlockFactor2 = "BlockFactor2";
    private Vector3 blockPos = new Vector3(0, 255, 0);

    private Integer newExprCounter() {return ++exprCounter; }

    public String getNewExprCounterString() {return "expr_" + newExprCounter(); }
    public String getExprCounterString() {return "expr_" + exprCounter; }

    public static String getPlayerTag() {
        return "active";
    }

    public Vector3 getNewBlockPos() {
        blockPos = new Vector3(blockPos.getX()+1, blockPos.getY(), blockPos.getZ());
        return blockPos;
    }

    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, getNewExprCounterString(), type1, type2, prefix);
    }

    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, getNewExprCounterString(), operator, getNewExprCounterString(), type1, type2, prefix);
    }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2, operator, prefix, getNewExprCounterString());
    }

    // EqualityExprST
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type, String prefix) {
        if (type == Type._block)
            return new EqualityExprST(a, b, operator, getNewExprCounterString(), blockFactor1Pos, blockFactor2Pos, prefix);
        else
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
//    public FuncCallST createFuncCallST(FuncEntry entry) {
//
//        var combinedST = entry.getOutput().stream().reduce(
//                (str1, str2) -> new BlankST(str1.getOutput() + str2.getOutput())
//        ).get();
//
//        String paramList = " ";
//
//        for (var param:entry.getParams()) {
//            paramList += entry.scope.lookup(param.getName()).prettyPrint() + ", ";
//        }
//        paramList += " ";
//
//        return new FuncCallST(entry.getName() + paramList + entry.toString(), combinedST.getOutput());
//    }

    public Template createFuncCallST(String name, boolean isMC, String prefix) {
        if (isMC)
            return FuncCallST.generateFuncCallToMC(name, "mcfuncs", prefix);
        return FuncCallST.generateFuncCallToNonMC(name, prefix);
    }

    // MCStatementsST
    public MCStatementST createMCStatementST(String command, String prefix) {
        return new MCStatementST(command, prefix);
    }

    // ParameterDependantStmntST
    public ParameterDependantStmntST createParamDependantStmntST(ParseTree ctx){
        return new ParameterDependantStmntST(ctx);
    }

    // FunctionDependantStmntST
    public FunctionDependantStmntST createFunctionDependantStmntST(ParseTree ctx){
        return new FunctionDependantStmntST(ctx);
    }

    // DclST
    public DclST createDclST(String varName, Type type, String prefix) {
        if (type == Type._block)
            return new DclST(varName, getNewBlockPos(), type, prefix);
        else
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

    public InstanST createInstanST(String varName, String exprName, Type type, String prefix) {
        return new InstanST(varName, exprName, type, prefix);
    }

    public InstanST createInstanST(String varName, int varVal, String prefix) {
        return new InstanST(varName, varVal, prefix);
    }

    public InstanST createInstanST(String varName, Vector3Value varVal, String prefix) {
        return new InstanST(varName, varVal, prefix);
    }

    // InstantST
    public InstanST createInstanST(String VarName, BlockValue blockValue, Vector3 pos, String prefix) {
        return new InstanST(VarName, blockValue, pos, prefix);
    }

    public AssignST createAssignST(String varName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, getExprCounterString(), prefix);
        return new AssignST(varName, getExprCounterString(), type, prefix);
    }

    public AssignST createAssignST(String varName, String exprName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, exprName, prefix);

        return new AssignST(varName, exprName, type, prefix);
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

    public static String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "_").substring(0,11);}
}
