import static org.junit.jupiter.api.Assertions.*;

import Logging.LogType;
import Logging.Logger;
import Logging.VariableAlreadyDeclaredError;
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
    public void FuncParameterNum() throws IOException {
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
    public void FuncParameterBlock() throws IOException {
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
    public void FuncParameterString() throws IOException {
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
    public void FuncParameterBoolean() throws IOException {
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
    public void FuncParameterVector2() throws IOException {
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
    public void FuncParameterVector3() throws IOException {
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
    public void FuncParameterMultiples() throws IOException {
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
    public void FuncParameterNameClash() throws IOException {
        helper.setupFromString("func Test(param1: num, param1: num) do\n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }

    @Test
    public void FuncBodyVariablesNameClash() throws IOException {
        helper.setupFromString("func Test() do\n var n: num \n var n: num \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertEquals(1, Logger.shared.getLogs().size());
        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }
    //endregion

    //region Test of scope for func body
    @Test
    public void FuncBodyOneVariableNum() throws IOException {
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
    public void FuncBodyMultipleVariables() throws IOException {
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
    public void FuncBodyVariableSameNameAsParameter() throws IOException {
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
    public void ForLoopIteratorIsInForLoopScope() throws IOException {
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

        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
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
    //endregion

    //region Test of scope for while-loop body
    //endregion

    //region Test of scope for do-while-loop expression
    //endregion

    //region Test of scope for do-while-loop body
    //endregion

    //region Test of scope for if-stmnt body
    @Test
    public void IfStmntBodyTestIfBody() {
        helper.setupFromString("if true do \n var n: num \n endif");
        MinespeakParser.IfStmntContext tree = helper.minespeakParser.ifStmnt();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.body(0).scope, "n");
        String actualName = helper.getEntryName(tree.body(0).scope, "n");

        assertEquals(Type.NUM, actualType);
        assertEquals("n", actualName);
    }

    @Test
    public void IfStmntBodyTestElifBody() {
        helper.setupFromString("if false do \n elif true do \n var n: num \n endif");
        MinespeakParser.IfStmntContext tree = helper.minespeakParser.ifStmnt();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.body(1).scope, "n");
        String actualName = helper.getEntryName(tree.body(1).scope, "n");

        assertEquals(Type.NUM, actualType);
        assertEquals("n", actualName);
    }

    @Test
    public void IfStmntBodyTestElseBody() {
        helper.setupFromString("if true do \n else do \n var n: num \n endif");
        MinespeakParser.IfStmntContext tree = helper.minespeakParser.ifStmnt();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.body(1).scope, "n");
        String actualName = helper.getEntryName(tree.body(1).scope, "n");

        assertEquals(Type.NUM, actualType);
        assertEquals("n", actualName);
    }

    @Test
    public void IfStmntBodyTestDifferentVariablesInDifferentBodies() {
        helper.setupFromString("if true do \n var i: num \n elif true do \n var e: bool \n else do \n var s: block \n endif");
        MinespeakParser.IfStmntContext tree = helper.minespeakParser.ifStmnt();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.body(0).scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.body(1).scope, "e");
        int actualType3 = helper.getEntryTypeAsInt(tree.body(2).scope, "s");
        String actualName1 = helper.getEntryName(tree.body(0).scope, "i");
        String actualName2 = helper.getEntryName(tree.body(1).scope, "e");
        String actualName3 = helper.getEntryName(tree.body(2).scope, "s");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.BOOL, actualType2);
        assertEquals(Type.BLOCK, actualType3);
        assertEquals("i", actualName1);
        assertEquals("e", actualName2);
        assertEquals("s", actualName3);
    }

    @Test
    public void IfStmntBodyTestSameVariableInDifferentBodies() {
        helper.setupFromString("if true do \n var i: num \n elif true do \n var i: bool \n else do \n var i: block \n endif");
        MinespeakParser.IfStmntContext tree = helper.minespeakParser.ifStmnt();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.body(0).scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.body(1).scope, "i");
        int actualType3 = helper.getEntryTypeAsInt(tree.body(2).scope, "i");
        String actualName1 = helper.getEntryName(tree.body(0).scope, "i");
        String actualName2 = helper.getEntryName(tree.body(1).scope, "i");
        String actualName3 = helper.getEntryName(tree.body(2).scope, "i");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.BOOL, actualType2);
        assertEquals(Type.BLOCK, actualType3);
        assertEquals("i", actualName1);
        assertEquals("i", actualName2);
        assertEquals("i", actualName3);
    }
    //endregion

    //region Test of scope for dcls
    @Test
    public void DclSingleNumInstan() {
        helper.setupFromString("var i : num\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "i");
        String actualName = helper.getEntryName(tree.scope, "i");

        assertEquals(Type.NUM, actualType);
        assertEquals("i", actualName);
    }

    @Test
    public void DclMultipleInstansSameType() {
        helper.setupFromString("var i : num, j : num\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");
        String actualName2 = helper.getEntryName(tree.scope, "j");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.NUM, actualType2);
        assertEquals("i", actualName1);
        assertEquals("j", actualName2);
    }

    @Test
    public void DclMultipleInstansDifferentTypes() {
        helper.setupFromString("var i : num, j : bool\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");
        String actualName2 = helper.getEntryName(tree.scope, "j");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.BOOL, actualType2);
        assertEquals("i", actualName1);
        assertEquals("j", actualName2);
    }

    @Test
    public void DclMultipleInstansSameID() {
        helper.setupFromString("var i : num, i : bool\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        boolean duplicateExists = helper.entryExists(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");

        assertEquals(Type.NUM, actualType1);
        assertEquals("i", actualName1);
        assertFalse(duplicateExists);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }
    //endregion

    //region Test of scope for instantiations
    @Test
    public void InstanSingleNumInstan() {
        helper.setupFromString("var i : num = 1\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.scope, "i");
        String actualName = helper.getEntryName(tree.scope, "i");

        assertEquals(Type.NUM, actualType);
        assertEquals("i", actualName);
    }

    @Test
    public void InstanMultipleInstansSameType() {
        helper.setupFromString("var i : num = 1, j : num = 2\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");
        String actualName2 = helper.getEntryName(tree.scope, "j");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.NUM, actualType2);
        assertEquals("i", actualName1);
        assertEquals("j", actualName2);
    }

    @Test
    public void InstanMultipleInstansDifferentTypes() {
        helper.setupFromString("var i : num = 1, j : bool = 2\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        int actualType2 = helper.getEntryTypeAsInt(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");
        String actualName2 = helper.getEntryName(tree.scope, "j");

        assertEquals(Type.NUM, actualType1);
        assertEquals(Type.BOOL, actualType2);
        assertEquals("i", actualName1);
        assertEquals("j", actualName2);
    }

    @Test
    public void InstanMultipleInstansSameID() {
        helper.setupFromString("var i : num = 1, i : bool = 2\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");
        boolean duplicateExists = helper.entryExists(tree.scope, "j");

        String actualName1 = helper.getEntryName(tree.scope, "i");

        assertEquals(Type.NUM, actualType1);
        assertEquals("i", actualName1);
        assertFalse(duplicateExists);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }
    //endregion
}
