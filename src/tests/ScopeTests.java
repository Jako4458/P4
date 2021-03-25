import static org.junit.jupiter.api.Assertions.*;

import Logging.LogType;
import Logging.Logger;
import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actualName = helper.getEnrtyName(tree.scope, "param1");
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

        String actual1Name = helper.getEnrtyName(tree.scope, "param1");
        String expected1Name = "param1";
        String actual2Name = helper.getEnrtyName(tree.scope, "param2");
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
        assertEquals("Error at line 1: variable param1 has already been declared", Logger.shared.getLogs().get(0).message);
    }

    @Test
    public void FuncBodyVariablesNameClash() throws IOException {
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
    public void FuncBodyOneVariableNum() throws IOException {
        helper.setupFromString("func Test(param1: num) do\n var n: num \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        int actualType = helper.getEntryTypeAsInt(tree.funcBody().scope, "n");
        int expectedType = MinespeakParser.NUM;

        String actualName = helper.getEnrtyName(tree.funcBody().scope, "n");
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

        String actual1Name = helper.getEnrtyName(tree.funcBody().scope, "n");
        String expected1Name = "n";
        String actual2Name = helper.getEnrtyName(tree.funcBody().scope, "n");
        String expected2Name = "n";
        String actual3Name = helper.getEnrtyName(tree.funcBody().scope, "n");
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

        String actualParamName = helper.getEnrtyName(tree.funcBody().scope, "param1");
        String expectedParamName = "param1";
        String actualBodyName = helper.getEnrtyName(tree.funcBody().scope, "param1");
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
    //endregion

    //region Test of scope for for-loop body
    //endregion

    //region Test of scope for foreach-loop iterator
    //endregion

    //region Test of scope for foreach-loop body
    //endregion

    //region Test of scope for while-loop expression
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
