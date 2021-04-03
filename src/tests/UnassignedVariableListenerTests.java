import Logging.LogType;
import Logging.Logger;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnassignedVariableListenerTests {
    TestHelper testHelper = new TestHelper();

    @BeforeEach
    public void reset() {
        Logger.shared.clear();
    }

    @Test
    public void UnassignedVariableWarningIsLogged() {
        testHelper.setupFromString("minespeak \n func Test() do \n var n:bool \n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actual = Logger.shared.getLogs().size();
        LogType actualType = Logger.shared.getLogs().get(0).type;

        int expected = 1;
        LogType expectedType = LogType.WARNING;

        assertEquals(expected, actual);
        assertEquals(expectedType, actualType);
    }

    @Test
    public void UnassignedVariableWarningIsLogged2() {
        testHelper.setupFromString("minespeak \n func Test() do \n var n:bool \n var b:bool \n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actualSize = Logger.shared.getLogs().size();
        LogType actualType1 = Logger.shared.getLogs().get(0).type;
        LogType actualType2 = Logger.shared.getLogs().get(1).type;

        int expectedSize = 2;
        LogType expectedType1 = LogType.WARNING;
        LogType expectedType2 = LogType.WARNING;

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType1, actualType1);
    }

    @Test
    public void UnassignedVariableInsideInnerScope() {
        testHelper.setupFromString("minespeak \n func Test() do \n var n:bool = true \n if n do \n var n:num \n endif \n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actualSize = Logger.shared.getLogs().size();
        LogType actualType1 = Logger.shared.getLogs().get(0).type;

        int expectedSize = 1;
        LogType expectedType1 = LogType.WARNING;

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType1, actualType1);
    }

    @Test
    public void UnassignedVariableOuterScopeButAssignedInner() {
        testHelper.setupFromString("minespeak \n func Test() do \n var n:bool \n if n do \n var n:num = 1 \n endif \n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actualSize = Logger.shared.getLogs().size();
        LogType actualType1 = Logger.shared.getLogs().get(0).type;
        String actualText1 = Logger.shared.getLogs().get(0).getText();

        int expectedSize = 1;
        LogType expectedType1 = LogType.WARNING;
        String expectedText1 = "n";

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType1, actualType1);
        assertEquals(expectedText1, actualText1);
    }

    @Test
    public void MultipleDclsLineUnassignedVariable() {
        testHelper.setupFromString("minespeak \n func Test() do \n var n:bool, b:bool \n \n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actualSize = Logger.shared.getLogs().size();
        LogType actualType1 = Logger.shared.getLogs().get(0).type;
        LogType actualType2 = Logger.shared.getLogs().get(1).type;

        int expectedSize = 2;
        LogType expectedType1 = LogType.WARNING;
        LogType expectedType2 = LogType.WARNING;

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType1, actualType1);
        assertEquals(expectedType2, actualType2);
    }

    @Test
    public void MultipleDclsLineUnassignedVariableOuterScopeOnly() {
        testHelper.setupFromString("minespeak \n func Test() do \n var flag:bool = true\n var n:bool, b:bool  \n if flag do \n var n:num = 1 \n var b:num = 1 \nendif\n endfunc\n closespeak");
        MinespeakParser.ProgContext tree = testHelper.minespeakParser.prog();
        testHelper.walkTree(tree);

        int actualSize = Logger.shared.getLogs().size();
        LogType actualType1 = Logger.shared.getLogs().get(0).type;
        LogType actualType2 = Logger.shared.getLogs().get(1).type;

        int expectedSize = 2;
        LogType expectedType1 = LogType.WARNING;
        LogType expectedType2 = LogType.WARNING;

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType1, actualType1);
        assertEquals(expectedType2, actualType2);
    }
}
