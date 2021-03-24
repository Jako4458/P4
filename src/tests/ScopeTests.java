import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ScopeTests {
    private TestHelper helper = new TestHelper();

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

}
