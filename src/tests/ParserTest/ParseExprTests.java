import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ParseExprTests {
    private TestHelper helper = new TestHelper();

    /* Division */
    @RepeatedTest(2)
    void wrongDivision(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_division_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctDivision(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_division_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Minus */
    @RepeatedTest(2)
    void wrongMinus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_minus_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctMinus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_minus_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Multiply */
    @RepeatedTest(3)
    void wrongMultiply(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_multiply_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctMultiply(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_multiply_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Parenthesis */
    @RepeatedTest(3)
    void wrongParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_parenthesis_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_parenthesis_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Plus */
    @RepeatedTest(2)
    void wrongPlus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_plus_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctPlus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_plus_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Power */
    @RepeatedTest(3)
    void wrongPower(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_power_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctPower(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_power_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* NOT */
    @RepeatedTest(2)
    void wrongNOT(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_NOT_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctNOT(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_NOT_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Unary minus */
    @RepeatedTest(2)
    void wrongUnminus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_unminus_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void correctUnminus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_unminus_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Mod */
    @RepeatedTest(3)
    void wrongMod(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_mod_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctMod(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_mod_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Less than */
    @RepeatedTest(3)
    void wrongLess(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_less_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctLess(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_less_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Greater than */
    @RepeatedTest(3)
    void wrongGreater(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greater_wrong" + rI.getCurrentRepetition() + ".ms");
        if (rI.getCurrentRepetition() == 2) {
            assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
        }
        else {
            assertDoesNotThrow(() -> helper.minespeakParser.expr());
            assertNotEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
        }

    }

    @RepeatedTest(3)
    void correctGreater(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greater_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Less than or equal */
    @RepeatedTest(3)
    void wrongLessEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_lessEqual_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctLessEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_lessEqual_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Greater than or equal */
    @RepeatedTest(3)
    void wrongGreaterEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greaterEqual_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctGreaterEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greaterEqual_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Equal */
    @RepeatedTest(3)
    void wrongEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_equal_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_equal_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* Not equal */
    @RepeatedTest(3)
    void wrongNotEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_notEqual_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctNotEqual(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_notEqual_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /* And */
    @RepeatedTest(3)
    void wrongAnd(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_and_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctAnd(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_and_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */

    /*Or */
    @RepeatedTest(3)
    void wrongOr(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_or_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctOr(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_or_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */
}
