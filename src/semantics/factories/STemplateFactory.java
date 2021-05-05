import java.util.ArrayList;
import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;
    private boolean setComments = Main.setup.commenting;
    private ArrayList<String> varaibleNames = new ArrayList<>();
    public String factor1UUID = generateValidUUID();
    public String factor2UUID = generateValidUUID();
    public Vector3 blockFactor1Pos = new Vector3(0, 255, 0); // is dependant on dcl before block var uses
    public Vector3 blockFactor2Pos = new Vector3(1, 255, 0); // is dependant on dcl before block var uses
    public String BlockFactor1 = "BlockFactor1";
    public String BlockFactor2 = "BlockFactor2";
    private Vector3 blockPos = new Vector3(2, 255, 0);

    private Integer newExprCounter() {return ++exprCounter; }

    public String getNewExprCounterString() {return "expr_" + newExprCounter(); }
    public String getExprCounterString() {return "expr_" + exprCounter; }

    public static String getPlayerTag() {
        return "active";
    }

    public Template resetExpressions(){
        String tempString = "";

        for (; exprCounter > 0; exprCounter--) {
             tempString += "scoreboard objectives remove " + getExprCounterString() + "\n";
        }

        tempString += "scoreboard objectives remove " + factor1UUID + "\n";
        tempString += "scoreboard objectives remove " + factor2UUID + "\n";

         return new BlankST(tempString, "remove expressions",setComments);
    }

    public Template deleteVariables(){
        String tempString = "";

        for (String name: varaibleNames) {
            tempString += "scoreboard objectives remove " + name + "\n";
        }
        return new BlankST(tempString, "delete variables",setComments);
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
        varaibleNames.add(varName);
        if (type.getTypeAsInt() == Type.BLOCK) {
            return new InstanST(varName, exprName, getNewBlockPos(), blockFactor1Pos, prefix, setComments);
        }
        return new InstanST(varName, exprName, type, prefix, setComments);
    }

    public InstanST createInstanST(String varName, int varVal, String prefix) {
        varaibleNames.add(varName);
        return new InstanST(varName, varVal, prefix, setComments);
    }

    public InstanST createInstanST(String varName, Vector3Value varVal, String prefix) {
        varaibleNames.add(varName);
        return new InstanST(varName, varVal, prefix, setComments);
    }

    // InstantST
    public InstanST createInstanST(String varName, BlockValue blockValue, Vector3 pos, String prefix) {
        varaibleNames.add(varName);
        return new InstanST(varName, blockValue, pos, prefix, setComments);
    }

    public AssignST createAssignST(String varName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, getExprCounterString(), prefix, setComments);
        return new AssignST(varName, getExprCounterString(), type, prefix, setComments);
    }

    public static String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "_").substring(0,11);}
}
