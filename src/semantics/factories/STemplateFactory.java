import java.util.ArrayList;
import java.util.UUID;

public class STemplateFactory {
    private Integer exprCounter = 0;                                    // Counter used for naming expression variables
    private boolean setComments = Main.setup.commenting;                // Boolean if comments should be used in templates
    private ArrayList<String> variableNames = new ArrayList<>();        // List of variable names, used for cleanup
    public Vector3 blockFactor1Pos = new Vector3(0, 255, 0);    // Known position to be used for block operations
    public Vector3 blockFactor2Pos = new Vector3(1, 255, 0);    // Known position to be used for block operations
    private Vector3 blockPos = new Vector3(2, 255, 0);          // Stating position of block variables
    private ArrayList<String> exprNames = new ArrayList<>();            // List of expression names, used for cleanup
    private String exprString = Main.setup.nameMode.equals(NamingMode.readable) ? "expr_" : generateValidUUID(5); // Prefix for expressions names

    /**
     * Increments expression counter
     * @return New expression count
     */
    private Integer newExprCounter() {return ++exprCounter; }

    /**
     * Increments expression counter and adds the variable to exprString for cleanup
     * @param isVector Whether or not the expression represents a vector
     * @return New expression name
     */
    public String getNewExprCounterString(boolean isVector) {
        String exprName = exprString + newExprCounter();
        if(isVector) {
            exprNames.add(exprName + "_x");
            exprNames.add(exprName + "_y");
            exprNames.add(exprName + "_z");
        } else
            exprNames.add(exprName);
        return exprName;
    }

    /**
     * Get last generated expression name as (without any _x, _y, or _z)
     * @return Last generated expression name
     */
    public String getExprCounterString() {return exprString + exprCounter; }

    /**
     * Get the tag for the player
     * @return The player tag
     */
    public static String getPlayerTag() {
        return "active";
    }

    /**
     * Reset all expresions and set the counter to 0
     * @return Template for reseting expressions in MCFunction
     */
    public Template resetExpressions(){
        StringBuilder tempString = new StringBuilder();

        for (String name: exprNames) {
             tempString.append("scoreboard objectives remove ").append(name).append("\n");
        }
        exprCounter = 0;

        return new BlankST(tempString.toString(), "remove expressions",setComments);
    }

    /**
     * Delete and reset all variables
     * @return Templae for reseting variables in MCFunction
     */
    public Template deleteVariables(){
        StringBuilder tempString = new StringBuilder();

        for (String name: variableNames) {
            // if variable name is not an expression
            if (!name.startsWith(exprString))
                tempString.append("scoreboard objectives remove ").append(name).append("\n");
        }
        return new BlankST(tempString.toString(), "delete variables",setComments);
    }


    /**
     * Sets the new empty block pos for the next block variable
     * @return Vector to be used for new block variable
     */
    public Vector3 getNewBlockPos() {
        Vector3 retPos = blockPos;
        blockPos = new Vector3(blockPos.getX()+1, blockPos.getY(), blockPos.getZ());
        return retPos;
    }

    /**
     * Generate template for a new file
     * @param fileName Name of the new file
     * @param isMcfunction Whether or not the file should include an MCFunction
     * @return Template for generating the new file
     */
    public EnterNewFileST createEnterNewFileST (String fileName, boolean isMcfunction) {
        return new EnterNewFileST(fileName, isMcfunction, setComments);
    }

    /**
     * Generate template for exiting previously entered file
     * @return Template for file exit
     */
    public ExitFileST createExitFileST () {
        return new ExitFileST(setComments);
    }

    /**
     * Generate template for arithmetic operations (not including Pow and negation)
     * @param expr1Name Name of left operand
     * @param expr2Name Name of right operand
     * @param operator Operator to use (vaid are: '+', '-', '*', '/', and '%')
     * @param type1 Type of left operand
     * @param type2 Type of right operand
     * @param prefix MCFunction prefix
     * @return Template for arithmetic expression in MCFunction
     */
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String expr2Name, String operator, Type type1, Type type2, String prefix) {
        boolean isVector = eitherIsVector(type1, type2);
        return new ArithmeticExprST(expr1Name, expr2Name, operator, getNewExprCounterString(isVector), type1, type2, prefix, setComments);
    }


    /**
     * Generate template for arithmetic operations with last known expression (not including Pow and negation)
     * @param expr1Name Name of left operand
     * @param operator Operator to use (vaid are: '+', '-', '*', '/', and '%')
     * @param type1 Type of left operand
     * @param type2 Type of right operand
     * @param prefix MCFunction prefix
     * @return Template for arithmetic expression in MCFunction
     */
    public ArithmeticExprST createArithmeticExprST (String expr1Name, String operator, Type type1, Type type2, String prefix) {
        boolean isVector = eitherIsVector(type1, type2);
        return new ArithmeticExprST(expr1Name, getExprCounterString(), operator, getNewExprCounterString(isVector), type1, type2, prefix, setComments);
    }

    /**
     * Generate template for num Power operation
     * @param expr1Name Name of base
     * @param expr2Name Name of exponent
     * @param prefix MCFunction prefix
     * @return Template for Pow expression in MCFuntion
     */
    public ArrayList<Template> createPowTemplates (String expr1Name, String expr2Name, String prefix) {
        ArrayList<Template> ret = new ArrayList<>();
        String baseName = generateValidUUID();
        String counterName = generateValidUUID();
        String loopID = generateValidUUID();
        String temp = getNewExprCounterString(false);
        String acc = getNewExprCounterString(false);

        ret.add(ArithmeticExprST.createPowSetupTemplate(expr1Name, expr2Name, acc, baseName, counterName, loopID , prefix, setComments));
        ret.add(new EnterNewFileST(loopID, false, false));
        ret.add(ArithmeticExprST.createPowFuncTemplate(expr2Name, acc, temp, baseName, loopID, counterName, prefix, setComments));
        ret.add(new ExitFileST(false));
        return ret;
    }

    /**
     * Generate template for equality comparison
     * @param a Name of left operand
     * @param b Name of right operand
     * @param operator Operator to be used (valid are: '==' and '!=')
     * @param type Type of variables
     * @param prefix MCFunction prefix
     * @return Template for equality comparison in MCFunction
     */
    public EqualityExprST createEqualityExprST(String a, String b, String operator, Type type, String prefix) {
        if (type == Type._block)
            return new EqualityExprST(a, b, operator, getNewExprCounterString(false), blockFactor1Pos, blockFactor2Pos, prefix, setComments);
        else
            return new EqualityExprST(a, b, operator, getNewExprCounterString(type == Type._vector2 || type == Type._vector3), type, prefix, setComments);
    }

    /**
     * Generate template for logical expression between two bool variables
     * @param a Name of left operand
     * @param b Name of right operand
     * @param operator Operator to be used in the expression (valid are: 'and' and 'or')
     * @param prefix MCFunction prefix
     * @return Template for logical expression in MCFunction
     */
    public LogicalExprST createLogicalExprST(String a, String b, String operator, String prefix) {
        return new LogicalExprST(a, b, operator, prefix, getNewExprCounterString(false), generateValidUUID(), setComments);
    }

    /**
     * Generate template for negation for variable of type num or bool
     * @param a Name of expression to negate
     * @param operator Operator to be used (valid are: 'not' and '-')
     * @param prefix MCFunction prefix
     * @param type Type (valid are: num and bool)
     * @return Template for negation in MCFunction
     */
    public NegationExprST createNegationExprST(String a, String operator, String prefix, Type type) {
        return new NegationExprST(a, operator, prefix, getNewExprCounterString(type == Type._vector2 || type == Type._vector3), type, setComments);
    }

    /**
     * Generate template for relational expression for type num
     * @param a Name of left operand
     * @param b Name of right operand
     * @param operator Relational operator to use
     * @param prefix MCFuntion prefix
     * @return Template for relational comparison in MCFunction
     */
    public RelationExprST createRelationExprST(String a, String b, String operator, String prefix) {
        return new RelationExprST(a, b, operator, prefix, getNewExprCounterString(false), setComments);
    }

    /**
     * Generate template for function calls
     * @param name Name of the function to call
     * @param isMC Whether or not the function is an @mc function
     * @param isBuiltin Whether or not the function is a built-in function
     * @param prefix MCFuntion prefix
     * @return Template for the function call
     */
    public Template createFuncCallST(String name, boolean isMC, boolean isBuiltin, String prefix) {
        if (isBuiltin)
            return FuncCallST.generateFuncCallToMC(name, "builtin", prefix, setComments);
        if (isMC)
            return FuncCallST.generateFuncCallToMC(name, "mcfuncs", prefix, setComments);
        return FuncCallST.generateFuncCallToNonMC(name, prefix, setComments);
    }

    /**
     * Generate template for a MCStatement
     * @param command The MCStatement as string (without the '$' prefix)
     * @param prefix MCFunction prefix
     * @return Template for the MCStatement
     */
    public MCStatementST createMCStatementST(String command, String prefix) {
        return new MCStatementST(command, prefix, setComments);
    }

    /**
     * Instantiation of a variable to other variable known by name
     * @param varName Name of variable
     * @param exprName Name of variable which is assigned to varName
     * @param type Type of the variable
     * @param prefix MCFunction prefix
     * @return Template for the instantiation
     */
    public InstanST createInstanST(String varName, String exprName, Type type, String prefix) {
        if (type != Type._vector2 && type != Type._vector3)
            variableNames.add(varName);
        else {
            variableNames.add(varName + "_x");
            variableNames.add(varName + "_y");
            variableNames.add(varName + "_z");
        }

        if (type.getTypeAsInt() == Type.BLOCK) {
            return new InstanST(varName, exprName, getNewBlockPos(), blockFactor1Pos, prefix, setComments);
        }
        return new InstanST(varName, exprName, type, prefix, setComments);
    }

    /**
     * Instantiation of function parameter
     * @param varName Name of parameter
     * @param exprName Name of variable with the actual value of the parameter
     * @param type Type of the variable
     * @param prefix MCFunction prefix
     * @param funcName Name of the function
     * @return Template for the instantiation
     */
    public InstanST createInstanST(String varName, String exprName, Type type, String prefix, String funcName) {
        if (type != Type._vector2 && type != Type._vector3)
            variableNames.add(varName);
        else {
            variableNames.add(varName + "_x");
            variableNames.add(varName + "_y");
            variableNames.add(varName + "_z");
        }
        if (type.getTypeAsInt() == Type.BLOCK) {
            return new InstanST(varName, exprName, getNewBlockPos(), blockFactor1Pos, prefix, setComments, funcName);
        }
        return new InstanST(varName, exprName, type, prefix, setComments);
    }

    /**
     * Instantiation of num variable based on int value
     * @param varName Name of variable
     * @param varVal Value of the num
     * @param prefix MCFunction prefix
     * @return Template for the instantiation
     */
    public InstanST createInstanST(String varName, int varVal, String prefix) {
        variableNames.add(varName);
        return new InstanST(varName, varVal, prefix, setComments);
    }

    /**
     * Instantiation of vector variable based on value
     * @param varName Name of variable
     * @param varVal Value of the vector
     * @param prefix MCFunction prefix
     * @return Template for the instantiation
     */
    public InstanST createInstanST(String varName, Vector3Value varVal, String prefix) {
        variableNames.add(varName + "_x");
        variableNames.add(varName + "_y");
        variableNames.add(varName + "_z");
        return new InstanST(varName, varVal, prefix, setComments);
    }

    /**
     * Instantiation of block variable based on value
     * The block will be instantiated at a new position
     * @param varName Name of variable
     * @param blockValue Value of the block
     * @param prefix MCFunction prefix
     * @return Template for the instantiation
     */
    public InstanST createInstanST(String varName, BlockValue blockValue, String prefix) {
        variableNames.add(varName);
        return new InstanST(varName, blockValue, getNewBlockPos(), prefix, setComments);
    }

    /**
     * Assignment of variable to last expression
     * @param varName Name of the variable to assign
     * @param type Type of the variable
     * @param prefix MCFunction prefix
     * @return Template for the assignment
     */
    public AssignST createAssignST(String varName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, getExprCounterString(), prefix, setComments);
        return new AssignST(varName, getExprCounterString(), type, prefix, setComments);
    }

    /**
     * Assignment of variable to other variable known by name
     * @param varName Name of the variable to assign
     * @param exprName Name of variable which is assigned to varName
     * @param type Type of the variables
     * @param prefix MCFunction prefix
     * @return Template for the assignment
     */
    public AssignST createAssignST(String varName, String exprName, Type type, String prefix){
        if (type == Type._block)
            return new AssignST(varName, blockFactor1Pos, exprName, prefix, setComments);

        return new AssignST(varName, exprName, type, prefix, setComments);
    }

    /**
     * Generate a UUID that is valid for use on MCFunctions scoreboard
     * Will always have length 14 to support vectors (postfix of _x, _y, _z can be appended)
     * @return Unique UUID of length 14
     */
    public static String generateValidUUID() {return UUID.randomUUID().toString().replace("-", "").substring(0,14);}

    /**
     * Generate a UUID that is valid for use on MCFunctions scoreboard if length is 16 or under
     * @param stringLength The length of the UUID to generate (Must be 16 or under to be valid)
     * @return Unique UUID
     */
    public static String generateValidUUID(int stringLength) {return UUID.randomUUID().toString().replace("-", "").substring(0,stringLength);}
    private boolean eitherIsVector(Type type1, Type type2) {
        return type1 == Type._vector2 || type1 == Type._vector3 || type2 == Type._vector2 || type2 == Type._vector3;
    }
}
