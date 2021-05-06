import java.util.ArrayList;
import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    private boolean setComments = Main.setup.commenting;
    private ArrayList<String> variableNames = new ArrayList<>();
    public String factor1UUID = generateValidUUID();
    public String factor2UUID = generateValidUUID();
    public Vector3 blockFactor1Pos = new Vector3(0, 255, 0); // is dependant on dcl before block var uses
    public Vector3 blockFactor2Pos = new Vector3(1, 255, 0); // is dependant on dcl before block var uses
    public String BlockFactor1 = "BlockFactor1";
    public String BlockFactor2 = "BlockFactor2";
    private Vector3 blockPos = new Vector3(2, 255, 0);
    private String exprString = Main.setup.nameMode.equals(NamingMode.readable) ? "expr_" : generateValidUUID(5);

    private Integer newExprCounter() {return ++exprCounter; }

    public String getNewExprCounterString() {return exprString + newExprCounter(); }
    public String getExprCounterString() {return exprString + exprCounter; }

    public static String getPlayerTag() {
        return "active";
    }

    public Template resetExpressions(){
        StringBuilder tempString = new StringBuilder();

        for (; exprCounter > 0; exprCounter--) {
             tempString.append("scoreboard objectives remove ").append(getExprCounterString()).append("\n");
        }

        tempString.append("scoreboard objectives remove ").append(factor1UUID).append("\n");
        tempString.append("scoreboard objectives remove ").append(factor2UUID).append("\n");
        tempString.append("scoreboard objectives remove ").append(factor1UUID).append("_x\n");
        tempString.append("scoreboard objectives remove ").append(factor1UUID).append("_y\n");
        tempString.append("scoreboard objectives remove ").append(factor1UUID).append("_z\n");
        tempString.append("scoreboard objectives remove ").append(factor2UUID).append("_x\n");
        tempString.append("scoreboard objectives remove ").append(factor2UUID).append("_y\n");
        tempString.append("scoreboard objectives remove ").append(factor2UUID).append("_z\n");

         return new BlankST(tempString.toString(), "remove expressions",setComments);
    }

    public Template deleteVariables(){
        StringBuilder tempString = new StringBuilder();

        for (String name: variableNames) {
            if (!name.startsWith("expr_") && !(name.startsWith(factor1UUID) || name.startsWith(factor2UUID)))
                tempString.append("scoreboard objectives remove ").append(name).append("\n");
        }
        return new BlankST(tempString.toString(), "delete variables",setComments);
    }



    public Vector3 getNewBlockPos() {
        Vector3 retPos = blockPos;
        blockPos = new Vector3(blockPos.getX()+1, blockPos.getY(), blockPos.getZ());
        return retPos;
    }

    // EnterNewFileST
    public EnterNewFileST createEnterNewFileST (String fileName, boolean isMcfunction) {
        return new EnterNewFileST(fileName, isMcfunction, setComments);
    }

    // ExitFileST
    public ExitFileST createExitFileST () {
        return new ExitFileST(setComments);
    }

    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, expr2Name, operator, getNewExprCounterString(), type1, type2, prefix, setComments);
    }

    // ArithmeticExprST
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String operator, Type type1, Type type2, String prefix) {
        return new ArithmeticExprST(expr1Name, getExprCounterString(), operator, getNewExprCounterString(), type1, type2, prefix, setComments);
    }

    // EqualityExprST
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type, String prefix) {
        if (type == Type._block)
            return new EqualityExprST(a, b, operator, getNewExprCounterString(), blockFactor1Pos, blockFactor2Pos, prefix, setComments);
        else
            return new EqualityExprST(a, b, operator, getNewExprCounterString(), type, prefix, setComments);
    }

    // LogicalExprST
    public LogicalExprST createLogicalExprST(String a, String b, String operator, String prefix) {
        return new LogicalExprST(a, b, operator, prefix, getNewExprCounterString(), generateValidUUID(), setComments);
    }

    // NegationExprST
    public NegationExprST createNegationExprST(String a, String operator, String prefix, Type type) {
        return new NegationExprST(a, operator, prefix, getNewExprCounterString(), type, setComments);
    }

    // RelationExprST
    public RelationExprST createRelationExprST(String a, String b, String operator, String prefix) {
        return new RelationExprST(a, b, operator, prefix, getNewExprCounterString(), setComments);
    }

    public Template createFuncCallST(String name, boolean isMC, boolean isBuiltin, String prefix) {
        if (isBuiltin)
            return FuncCallST.generateFuncCallToMC(name, "builtin", prefix, setComments);
        if (isMC)
            return FuncCallST.generateFuncCallToMC(name, "mcfuncs", prefix, setComments);
        return FuncCallST.generateFuncCallToNonMC(name, prefix, setComments);
    }

    // MCStatementsST
    public MCStatementST createMCStatementST(String command, String prefix) {
        return new MCStatementST(command, prefix, setComments);
    }

    // InstantST
    public InstanST createInstanST(String varName, String exprName, String prefix) {
        return new InstanST(varName, exprName, prefix, setComments);
    }

    public InstanST createInstanST(String varName, String exprName, Type type, String prefix) {
        variableNames.add(varName);
        if (type.getTypeAsInt() == Type.BLOCK) {
            return new InstanST(varName, exprName, getNewBlockPos(), blockFactor1Pos, prefix, setComments);
        }
        return new InstanST(varName, exprName, type, prefix, setComments);
    }

    public InstanST createInstanST(String varName, int varVal, String prefix) {
        variableNames.add(varName);
        return new InstanST(varName, varVal, prefix, setComments);
    }

    public InstanST createInstanST(String varName, Vector3Value varVal, String prefix) {
        variableNames.add(varName);
        return new InstanST(varName, varVal, prefix, setComments);
    }

    // InstantST
    public InstanST createInstanST(String varName, BlockValue blockValue, Vector3 pos, String prefix) {
        variableNames.add(varName);
        return new InstanST(varName, blockValue, pos, prefix, setComments);
    }

    public AssignST createAssignST(String varName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, getExprCounterString(), prefix, setComments);
        return new AssignST(varName, getExprCounterString(), type, prefix, setComments);
    }

    public AssignST createAssignST(String varName, String exprName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, exprName, prefix, setComments);

        return new AssignST(varName, exprName, type, prefix, setComments);
    }

    public static String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "").substring(0,14);}
    public static String generateValidUUID(int stringLength) {return UUID.randomUUID().toString().replace("-", "").substring(0,stringLength);}

}
