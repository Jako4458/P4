import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import utils.MSValue;

import java.io.IOException;

public class ParseExprTests {
    private TestHelper helper = new TestHelper();

    @Test
    void TestTest(){
        helper.setupFromString("2 Pow 3");

        ScopeVisitor scopeVisitor = new ScopeVisitor();

        MinespeakParser.PowContext powContext = (MinespeakParser.PowContext) helper.minespeakParser.expr();

        MSValue result = scopeVisitor.visit(powContext);

        assertEquals(8, result.getValue());
    }

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
    }
    /* ------- */

    /* Greater than */
    @RepeatedTest(3)
    void wrongGreater(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greater_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.expr());
    }

    @RepeatedTest(3)
    void correctGreater(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_greater_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
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
    }
    /* ------- */
}
