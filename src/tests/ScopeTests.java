import static org.junit.jupiter.api.Assertions.*;

import Logging.LogType;
import Logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ScopeTests {
    private TestHelper helper = new TestHelper();

    @BeforeEach
    public void reset() {
        Logger.shared.clear();
    }

    //region Test of scope for func parameters
    @Test
    public void FuncParameterNum() {
        helper.setupFromString("func Test(param1: num) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.NUM;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterBlock() {
        helper.setupFromString("func Test(param1: block) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.BLOCK;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterString() {
        helper.setupFromString("func Test(param1: string) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.STRING;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterBoolean() {
        helper.setupFromString("func Test(param1: bool) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.BOOL;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterVector2() {
        helper.setupFromString("func Test(param1: vector2) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.VECTOR2;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterVector3() {
        helper.setupFromString("func Test(param1: vector3) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedType = MinespeakParser.VECTOR3;

        String actualName = helper.getEntryName(tree.scope, "param1");
        String expectedName = "param1";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncParameterMultiples() {
        helper.setupFromString("func Test(param1: vector3, param2: num) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actual1Type = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expected1Type = MinespeakParser.VECTOR3;
        int actual2Type = helper.getEntryTypeAsInt(tree.scope, "param2");
        int expected2Type = MinespeakParser.NUM;

        String actual1Name = helper.getEntryName(tree.scope, "param1");
        String expected1Name = "param1";
        String actual2Name = helper.getEntryName(tree.scope, "param2");
        String expected2Name = "param2";

        assertEquals(expected1Type, actual1Type);
        assertEquals(expected1Name, actual1Name);
        assertEquals(expected2Type, actual2Type);
        assertEquals(expected2Name, actual2Name);
    }

    @Test
    public void FuncParameterNameClash() {
        helper.setupFromString("func Test(param1: num, param1: num) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertEquals("Error at line 1: variable param1 has already been declared", Logger.shared.getLogs().get(0).message);
    }

    @Test
    public void FuncBodyVariablesNameClash() {
        helper.setupFromString("func Test() do\n var n: num \n var n: num \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertEquals("Error at line 3: variable n has already been declared", Logger.shared.getLogs().get(0).message);
    }
    //endregion

    //region Test of scope for func body
    @Test
    public void FuncBodyOneVariableNum() {
        helper.setupFromString("func Test(param1: num) do\n var n: num \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.funcBody().scope, "n");
        int expectedType = MinespeakParser.NUM;

        String actualName = helper.getEntryName(tree.funcBody().scope, "n");
        String expectedName = "n";

        assertEquals(expectedType, actualType);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void FuncBodyMultipleVariables() {
        helper.setupFromString("func Test(param1: num) do\n var n: num \n var b: block \n var s: string \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actual1Type = helper.getEntryTypeAsInt(tree.funcBody().scope, "n");
        int expected1Type = MinespeakParser.NUM;
        int actual2Type = helper.getEntryTypeAsInt(tree.funcBody().scope, "n");
        int expected2Type = MinespeakParser.NUM;
        int actual3Type = helper.getEntryTypeAsInt(tree.funcBody().scope, "n");
        int expected3Type = MinespeakParser.NUM;

        String actual1Name = helper.getEntryName(tree.funcBody().scope, "n");
        String expected1Name = "n";
        String actual2Name = helper.getEntryName(tree.funcBody().scope, "n");
        String expected2Name = "n";
        String actual3Name = helper.getEntryName(tree.funcBody().scope, "n");
        String expected3Name = "n";

        assertEquals(expected1Type, actual1Type);
        assertEquals(expected1Name, actual1Name);
        assertEquals(expected2Type, actual2Type);
        assertEquals(expected2Name, actual2Name);
        assertEquals(expected3Type, actual3Type);
        assertEquals(expected3Name, actual3Name);
    }

    @Test
    public void FuncBodyVariableSameNameAsParameter() {
        helper.setupFromString("func Test(param1: num) do\n var param1: bool \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualParamType = helper.getEntryTypeAsInt(tree.scope, "param1");
        int expectedParamType = MinespeakParser.NUM;
        int actualBodyType = helper.getEntryTypeAsInt(tree.funcBody().scope, "param1");
        int expectedBodyType = MinespeakParser.BOOL;

        String actualParamName = helper.getEntryName(tree.funcBody().scope, "param1");
        String expectedParamName = "param1";
        String actualBodyName = helper.getEntryName(tree.funcBody().scope, "param1");
        String expectedBodyName = "param1";

        assertEquals(expectedParamType, actualParamType);
        assertEquals(expectedParamName, actualParamName);
        assertEquals(expectedBodyType, actualBodyType);
        assertEquals(expectedBodyName, actualBodyName);

        assertNotEquals(actualBodyType, actualParamType);
        assertEquals(actualBodyName, actualParamName);
    }
    //endregion


    //region Test of scope for for-loop iterator
    @Test
    public void ForLoopIteratorIsInForLoopScope() {
        helper.setupFromString("for var i: num = 0 until i < 10 where i += 1 do \n var i : bool = true \n endfor");
        MinespeakParser.ForStmntContext tree = helper.minespeakParser.forStmnt();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.scope, "i");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.body().scope, "i");
        String expectedIteratorName = "i";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }
    //endregion

    //region Test of scope for for-loop body
    @Test
    public void ForLoopBodyVarAlreadyDeclared() throws IOException {
        helper.setupFromString("for var i: num = 0 until i < 10 where i += 1 do \n var test : bool = true \n var test : bool = true \n endfor");
        MinespeakParser.ForStmntContext tree = helper.minespeakParser.forStmnt();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.scope, "i");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.body().scope, "i");
        String expectedIteratorName = "i";


        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
    }

    @Test
    public void ForLoopBodyNewIHasCorrectType() throws IOException {
        helper.setupFromString("for var i: num = 0 until i < 10 where i += 1 do \n var i : bool = true \n endfor");
        MinespeakParser.ForStmntContext tree = helper.minespeakParser.forStmnt();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.body().scope, "i");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.body().scope, "i");
        String expectedIteratorName = "i";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void ForLoopBodyGetIterator() throws IOException {
        helper.setupFromString("for var i: num = 0 until i < 10 where i += 1 do \n i = 1 \n endfor");
        MinespeakParser.ForStmntContext tree = helper.minespeakParser.forStmnt();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.body().scope, "i");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.body().scope, "i");
        String expectedIteratorName = "i";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }
    //endregion

    //region Test of scope for foreach-loop iterator
    @Test
    public void ForEachIteratorInScope() throws IOException {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().foreach().scope, "number");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().foreach().scope, "number");
        String expectedIteratorName = "number";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);


    }

    @Test
    public void ForEachIteratorInScope() throws IOException {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
    }
    //endregion

    //region Test of scope for foreach-loop body
    @Test
    public void ForEachLoopVarInBody() throws IOException {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        String expectedIteratorName = "number";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void ForEachLoopSameLoopVarNameInBody() throws IOException {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n var number : bool = 5 \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        String expectedIteratorName = "number";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    //endregion

    //region Test of scope for while-loop expression
    @Test
    public void WhileExpressionIsInWhileScope() throws IOException {
        helper.setupFromString("var flag : bool = true \n while flag do \n endwhile \n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualLoopVarType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().whileStmnt().scope, "flag");
        int expectedLoopVarType = MinespeakParser.BOOL;

        String actualLoopVarName = helper.getEntryName(tree.stmnts().stmnt(1).loop().whileStmnt().scope, "flag");
        String expectedLoopVarName = "flag";

        assertEquals(expectedLoopVarType, actualLoopVarType);
        assertEquals(expectedLoopVarName, actualLoopVarName);
    }

    //endregion

    //region Test of scope for while-loop body
    //endregion

    //region Test of scope for do-while-loop expression
    //endregion

    //region Test of scope for do-while-loop body
    //endregion

    //region Test of scope for if-stmnt condition
    //endregion

    //region Test of scope for if-stmnt body
    //endregion

    //region Test of scope for dcls
    //endregion

    //region Test of scope for instantiations
    //endregion
}
