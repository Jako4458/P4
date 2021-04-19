import org.antlr.v4.runtime.tree.ParseTree;

import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    private Integer vecExprCounter = 0;
    public String factor1UUID = generateValidUUID();
    public String factor2UUID = generateValidUUID();
    public String vecFactor1UUID = generateValidUUID();
    public String vecFactor2UUID = generateValidUUID();

    private Integer getExprCounter() {return ++exprCounter; }


    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2) {
        return createArithmeticExprST(expr1Name, expr2Name, operator, type1, type2, "");
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, "expr_" + getExprCounter(), type1, type2, prefix);
    }
    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator) {
        return createArithmeticExprST(expr1Name, expr2, operator, "");
    }

    public ArithmeticExprST createArithmeticExprST (String expr1Name, int expr2, String operator, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2, operator, prefix, "expr_" + getExprCounter());
    }

    // EqualityExprST
    public EqualityExprST CreateEqualityExprST (String a, String b, String operator) {
        return CreateEqualityExprST(a, b, operator, "");
    }
    public EqualityExprST CreateEqualityExprST (String a, String b, String operator, String prefix) {
        return new EqualityExprST(a, b, operator, prefix, "expr_" + getExprCounter());
    }

    // LogicalExprST
    public LogicalExprST CreateLogicalExprST(String a, String b, String operator) {
        return CreateLogicalExprST(a, b, operator, "");
    }
    public LogicalExprST CreateLogicalExprST(String a, String b, String operator, String prefix) {
        return new LogicalExprST(a, b, operator, prefix, "expr_" + getExprCounter(), generateValidUUID());
    }

    // NegationExprST
    public NegationExprST CreateNegationExprST(String a, String operator) {
        return CreateNegationExprST(a, operator, "");
    }
    public NegationExprST CreateNegationExprST(String a, String operator, String prefix) {
        return new NegationExprST(a, operator, prefix, "expr_" + getExprCounter());
    }

    // RelationExprST
    public RelationExprST CreateRelationExprST(String a, String b, String operator) {
        return CreateRelationExprST(a, b, operator, "");
    }
    public RelationExprST CreateRelationExprST(String a, String b, String operator, String prefix) {
        return new RelationExprST(a, b, operator, prefix, "expr_" + getExprCounter());
    }


    // MCFuncCallST
    public MCFuncCallST CreateMCFuncCallST(FuncEntry entry) {
        return new MCFuncCallST(entry.getName());
    }

    // FuncCallST
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

    // MCStatementsST
    public MCStatementST CreateMCStatementST(String command) {
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
    public InstanST createInstanST(SymEntry entry) {
        if (entry.getType() == Type._vector2)
            return new InstanST(entry.getVarName(), entry.getValue().getCasted(Vector2Value.class));
        if (entry.getType() == Type._vector3)
            return new InstanST(entry.getVarName(), entry.getValue().getCasted(Vector3Value.class));

        return new InstanST(entry.getVarName(), "expr_" + exprCounter.toString());
    }

    public String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "_").substring(0,11);}
//    private String getValAsString(SymEntry entry) {
//
//        if (entry.getType() == Type._num)
//            return Value.value(entry.getValue().getCasted(NumValue.class)).toString();
//        if (entry.getType() == Type._bool)
//            return Value.value(entry.getValue().getCasted(BoolValue.class)) ? "1" : "0" ;
//        if (entry.getType() == Type._vector2)
//            return Value.value(entry.getValue().getCasted(Vector2Value.class)).toString("~");
//        if (entry.getType() == Type._vector3)
//            return Value.value(entry.getValue().getCasted(Vector3Value.class)).toString("~");
//
//        return entry.toString();
//    }
}
