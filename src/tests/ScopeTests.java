import static org.junit.jupiter.api.Assertions.*;

import logging.LogType;
import logging.Logger;
import logging.logs.VariableAlreadyDeclaredError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }

    @Test
    public void FuncBodyVariablesNameClash() {
        helper.setupFromString("func Test() do\n var n: num \n var n: num \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertEquals(LogType.ERROR, Logger.shared.getLogs().get(0).type);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
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
    public void ForLoopBodyVarAlreadyDeclared() {
        helper.setupFromString("for var i: num = 0 until i < 10 where i += 1 do \n var test : bool = true \n var test : bool = true \n endfor");
        MinespeakParser.ForStmntContext tree = helper.minespeakParser.forStmnt();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }

    @Test
    public void ForLoopBodyNewIHasCorrectType() {
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
    public void ForLoopBodyGetIterator() {
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
    public void ForEachIteratorInScope() {
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
    public void ForEachLoopVarInBody() {
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
    public void ForEachLoopSameLoopVarNameInBody() {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n var number : bool = true \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().foreach().body().scope, "number");
        String expectedIteratorName = "number";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void ForEachBodyVarAlreadyDeclaredError() {
        helper.setupFromString("var some_array : num[] \n foreach num number in some_array do \n var test1:num = 0 \n var test1:num = 0 \n endfor\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }
    //endregion

    //region Test of scope for while-loop expression
    @Test
    public void WhileExpressionIsInWhileScope() {
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
    @Test
    public void WhileLoopVarInBody() {
        helper.setupFromString("var flag : bool = true \n while flag do \n \n endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().whileStmnt().body().scope, "flag");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().whileStmnt().body().scope, "flag");
        String expectedIteratorName = "flag";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void WhileLoopVarNameInBody() {
        helper.setupFromString("var flag : bool = true \n while flag do \n var flag : num = 1 \n endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().whileStmnt().body().scope, "flag");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().whileStmnt().body().scope, "flag");
        String expectedIteratorName = "flag";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void WhileBodyVarAlreadyDeclaredError() {
        helper.setupFromString("var flag : bool = true \n while flag do \n var flag : num = 1 \n var flag : num = 5 \n endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }

    //endregion

    //region Test of scope for do-while-loop expression
    @Test
    public void DoWhileLoopVarInLoopScope() {
        helper.setupFromString("var flag : bool = true \n do /*statements*/ \n while flag endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().doWhile().scope, "flag");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().doWhile().scope, "flag");
        String expectedIteratorName = "flag";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }



    //endregion

    //region Test of scope for do-while-loop body
    @Test
    public void DoWhileLoopVarNameInBody() {
        helper.setupFromString("var flag : bool = true \n do \n var flag : num = 5 \n while flag endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().doWhile().body().scope, "flag");
        int expectedIteratorType = MinespeakParser.NUM;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().doWhile().body().scope, "flag");
        String expectedIteratorName = "flag";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void DoWhileLoopVarNotFromBody() {
        helper.setupFromString("var flag : bool = true \n do \n var flag : num = 5 \n while flag endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualIteratorType = helper.getEntryTypeAsInt(tree.stmnts().stmnt(1).loop().doWhile().scope, "flag");
        int expectedIteratorType = MinespeakParser.BOOL;

        String actualIteratorName = helper.getEntryName(tree.stmnts().stmnt(1).loop().doWhile().scope, "flag");
        String expectedIteratorName = "flag";

        assertEquals(expectedIteratorType, actualIteratorType);
        assertEquals(expectedIteratorName, actualIteratorName);
    }

    @Test
    public void DoWhileBodyVarAlreadyDeclaredError() {
        helper.setupFromString("var flag : bool = true \n do \n var flag : num = 5 \n var flag : num = 10 \n while flag endwhile\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }

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
        helper.setupFromString("var i : num = 1, i : bool = true\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        int actualType1 = helper.getEntryTypeAsInt(tree.scope, "i");

        String actualName1 = helper.getEntryName(tree.scope, "i");

        assertEquals(Type.NUM, actualType1);
        assertEquals("i", actualName1);
        assertTrue(Logger.shared.getLogs().get(0) instanceof VariableAlreadyDeclaredError);
    }
    //endregion

    //region Test of simple entries in a scope
    @ParameterizedTest(name = "{index} => entry with modifier {1}")
    @MethodSource("modifierToName")
    public void SimpleEntryGetModifier(int mod, String name) {
        helper.setupFromString(String.format("%s i : num = 1\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof SimpleEntry);
        assertEquals(mod, actualEntry.getModifier());
    }

    @ParameterizedTest(name = "{index} => entry with type {1}")
    @MethodSource("typeToName")
    public void SimpleEntryGetType(int type, String name) {
        helper.setupFromString(String.format("const i : %s\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof SimpleEntry);
        assertEquals(Type.getTypeFromInt(type), actualEntry.getType());
    }

    @ParameterizedTest(name = "{index} => entry with name {0}")
    @ValueSource(strings = {"i", "j", "any_string", "aValidName2"})
    public void SimpleEntryGetName(String name) {
        helper.setupFromString(String.format("const %s : num\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup(name);
        assertTrue(actualEntry instanceof SimpleEntry);
        assertEquals(name, actualEntry.getName());
    }

    @Test
    public void SimpleEntryGetCTXIsDclsContext() {
        helper.setupFromString("const i : num\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof SimpleEntry);
        assertTrue(actualEntry.getCtx() instanceof MinespeakParser.DclsContext);
    }
    //endregion

    //region Test of function entries in scope
    @ParameterizedTest(name = "{index} => entry with return type {1}")
    @MethodSource("typeToName")
    public void FuncEntryGetTypeHasReturnType(int type, String name) {
        helper.setupFromString(String.format("func Test() -> %s do \n \n endfunc \n", name));
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get("Test");
        assertNotNull(actualEntry);
        assertEquals(Type.getTypeFromInt(type), actualEntry.getType());
    }

    @ParameterizedTest(name = "{index} => entry with name {0}")
    @ValueSource(strings = {"i", "j", "any_string", "aValidName2"})
    public void FuncEntryGetName(String name) {
        helper.setupFromString(String.format("func %s() do \n \n endfunc \n", name));
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get(name);
        assertNotNull(actualEntry);
        assertEquals(name, actualEntry.getName());
    }

    @Test
    public void FuncEntryFunctionIsMCFunction() {
        helper.setupFromString("@mc\nfunc Test() do \n \n endfunc \n");
        MinespeakParser.McFuncContext tree = helper.minespeakParser.mcFunc();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get("Test");
        assertNotNull(actualEntry);
        assertEquals("Test", actualEntry.getName());
        assertTrue(actualEntry.isMCFunction());
    }

    @Test
    public void FuncEntryModifierIsConst() {
        helper.setupFromString("@mc\nfunc Test() do \n \n endfunc \n");
        MinespeakParser.McFuncContext tree = helper.minespeakParser.mcFunc();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get("Test");
        assertNotNull(actualEntry);
        assertEquals(MinespeakParser.CONST, actualEntry.getModifier());
    }

    @Test
    public void FuncEntryGetCTX() {
        helper.setupFromString("@mc\nfunc Test() do \n \n endfunc \n");
        MinespeakParser.McFuncContext tree = helper.minespeakParser.mcFunc();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get("Test");
        assertNotNull(actualEntry);
        assertNotNull(actualEntry.getCtx());
        assertEquals("Test", actualEntry.getCtx().ID().getText());
    }

    @ParameterizedTest(name = "{index} => params are \"{1}\"")
    @MethodSource("paramToList")
    public void FuncEntryVariableParameters(int numOfParams, String asString, List<String> paramNames, List<Integer> paramTypes) {
        helper.setupFromString(String.format("func Test(%s) do \n \n endfunc \n", asString));
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        FuncEntry actualEntry = helper.getListener().getFunctions().get("Test");
        assertNotNull(actualEntry);
        assertEquals(numOfParams, actualEntry.getParams().size());
        for (int i = 0; i < numOfParams; i++) {
            assertEquals(paramNames.get(i), actualEntry.getParams().get(i).getName());
            assertEquals(paramTypes.get(i), actualEntry.getParams().get(i).getType().getTypeAsInt());
        }
    }
    //endregion

    //region Test of array entries in scope
    @ParameterizedTest(name = "{index} => array entry with modifier {1}")
    @MethodSource("modifierToName")
    public void ArrayEntryGetModifier(int mod, String name) {
        helper.setupFromString(String.format("%s i : num[] = 1\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof ArrayEntry);
        assertEquals(mod, actualEntry.getModifier());
    }

    @ParameterizedTest(name = "{index} => array entry with type {1}[]")
    @MethodSource("typeToName")
    public void ArrayEntryGetType(int type, String name) {
        helper.setupFromString(String.format("const i : %s[]\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof ArrayEntry);
        assertTrue(actualEntry.getType() instanceof ArrayType);
        assertEquals(Type.getTypeFromInt(type), ((ArrayType) actualEntry.getType()).type);
    }

    @ParameterizedTest(name = "{index} => array entry with base type {1}")
    @MethodSource("typeToName")
    public void ArrayEntryGetBaseType(int type, String name) {
        helper.setupFromString(String.format("const i : %s[]\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof ArrayEntry);
        assertTrue(actualEntry.getType() instanceof ArrayType);
        assertEquals(Type.getTypeFromInt(type), ((ArrayEntry) actualEntry).getBaseType());
    }

    @ParameterizedTest(name = "{index} => array entry with name {0}")
    @ValueSource(strings = {"i", "j", "any_string", "aValidName2"})
    public void ArrayEntryGetName(String name) {
        helper.setupFromString(String.format("const %s : num[]\n", name));
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup(name);
        assertTrue(actualEntry instanceof ArrayEntry);
        assertEquals(name, actualEntry.getName());
    }

    @Test
    public void ArrayEntryGetCTXIsDclsContext() {
        helper.setupFromString("const i : num[]\n");
        MinespeakParser.BodyContext tree = helper.minespeakParser.body();
        helper.walkTree(tree);

        SymEntry actualEntry = tree.scope.lookup("i");
        assertTrue(actualEntry instanceof ArrayEntry);
        assertTrue(actualEntry.getCtx() instanceof MinespeakParser.DclsContext);
    }
    //endregion

    //region Helper functions for testing
    private static Stream<Arguments> typeToName() {
        return Stream.of(
                Arguments.arguments(Type.NUM, "num"),
                Arguments.arguments(Type.BOOL, "bool"),
                Arguments.arguments(Type.BLOCK, "block"),
                Arguments.arguments(Type.STRING, "string"),
                Arguments.arguments(Type.VECTOR2, "vector2"),
                Arguments.arguments(Type.VECTOR3, "vector3")
        );
    }

    private static Stream<Arguments> modifierToName() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.CONST, "const"),
                Arguments.arguments(MinespeakParser.VAR, "var")
        );
    }

    private static Stream<Arguments> paramToList() {
        return Stream.of(
                Arguments.arguments(0, "",
                        new ArrayList<String>(),
                        new ArrayList<Integer>()
                ),
                Arguments.arguments(1, "param1: num",
                        Arrays.asList("param1"),
                        Arrays.asList(Type.NUM)
                ),
                Arguments.arguments(2, "param1: num, param2: bool",
                        Arrays.asList("param1", "param2"),
                        Arrays.asList(Type.NUM, Type.BOOL)
                )
        );
    }
    //endregion
}
