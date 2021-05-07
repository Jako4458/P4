import logging.Logger;
import logging.logs.ConstantLoopExpressionWarning;
import logging.logs.InfiniteLoopWarning;
import logging.logs.Log;
import logging.logs.UnreachableCodeWarning;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoopExprCheckerTests {
    private TestHelper helper = new TestHelper();

    @BeforeEach
    public void reset() {
        Logger.shared.clear();
    }
    //region While Expr Test
    @Test
    public void WhileExprConstantTrue() {
        helper.setupFromString("minespeak \n func test() do \n while true do \n endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof InfiniteLoopWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    @Test
    public void WhileExprConstantFalse() {
        helper.setupFromString("minespeak \n func test() do \n while false do \n endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof UnreachableCodeWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    @Test
    public void WhileExprRValueIsNeverAssignedToInLoopBody() {
        helper.setupFromString("minespeak \n  func test() do \nvar i : num = 0 \n while i < 5 do \n endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof ConstantLoopExpressionWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    @Test
    public void WhileExprRValueIsAssignedToInLoopBody() {
        helper.setupFromString("minespeak \n  func test() do \nvar i : num = 0 \n while i < 5 do \n i += 1 \n endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        assertEquals(0, actual);
    }

    @Test
    public void WhileExprRValueIsAssignedToInNestedLoopBody() {
        helper.setupFromString("minespeak \n" +
                "  func test() do \n" +
                "var i : num = 0 \n" +
                " while i < 5 do \n" +
                "while i < 10 do \n" +
                " i+=1\n" +
                " endwhile\n" +
                " endwhile \n" +
                " endfunc \n" +
                " closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        assertEquals(0, actual);
    }

    @Test
    public void WhileExprRValueIsAssignedToInLoopBodyButNotNestedBody() {
        helper.setupFromString("minespeak \n" +
                "  func test() do \n" +
                "var i : num = 0 \n" +
                " while i < 5 do \n" +
                " i+=1\n" +
                "while i < 10 do \n" +
                " endwhile\n" +
                " endwhile \n" +
                " endfunc \n" +
                " closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof ConstantLoopExpressionWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    @Test
    public void WhileExprNestedNoneAssigned() {
        helper.setupFromString("minespeak \n" +
                "func test() do \n" +
                "  var i : num = 0 \n" +
                "  while i < 5 do \n" +
                "    while i < 10 do \n" +
                "    endwhile\n" +
                "  endwhile \n" +
                "endfunc \n" +
                "closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool1 = Logger.shared.getLogs().get(0) instanceof ConstantLoopExpressionWarning;
        boolean actualBool2 = Logger.shared.getLogs().get(0) instanceof ConstantLoopExpressionWarning;


        assertEquals(2, actual);
        assertTrue(actualBool1);
        assertTrue(actualBool2);
    }

    //endregion

    //region for expr test
    @Test
    public void ForConditionConstantTrue() {
        helper.setupFromString("minespeak \n func test() do \n for var i : num = 0 until true where i+=1 do \n endfor \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof InfiniteLoopWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }


    @Test
    public void ForConditionConstantFalse() {
        helper.setupFromString("minespeak \n func test() do \n for var i : num = 0 until false where i+=1 do \n endfor \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof UnreachableCodeWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }
    //endregion

    //region do while test
    @Test
    public void DoWhileConditionConstantTrue() {
        helper.setupFromString("minespeak \n func test() do \n do\n while true endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof InfiniteLoopWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    @Test
    public void DoWhileConditionConstantFalse() {
        helper.setupFromString("minespeak \n func test() do \n do\n while false endwhile \n endfunc \n closespeak");
        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        MinespeakParser.ProgContext tree = helper.minespeakParser.prog();
        helper.walkTree(tree);
        helper.walkTreeWithListener(tree, infiniteLoopDetectionListener);

        int actual = Logger.shared.getLogs().size();
        boolean actualBool = Logger.shared.getLogs().get(0) instanceof ConstantLoopExpressionWarning;

        assertEquals(1, actual);
        assertTrue(actualBool);
    }

    //endregion
}
