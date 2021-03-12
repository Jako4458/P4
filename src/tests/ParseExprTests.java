import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class ParseExprTests {
    private TestHelper helper = new TestHelper();

    /* Division */
    @RepeatedTest(2)
    void wrongDivision(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_division_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
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
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctMinus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_minus_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */

    /* Multiply */
    @RepeatedTest(2)
    void wrongMultiply(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_multiply_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
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
        helper.setupFromFile("/parser/test/parser/expr/expr_binary_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_parenthesis_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */

    /* Plus */
    @RepeatedTest(2)
    void wrongPlus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_plus_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctPlus(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_plus_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */

    /* Power */
    @RepeatedTest(2)
    void wrongPower(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_power_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctPower(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_power_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */

    /* Unary */
    @RepeatedTest(2)
    void wrongUnary(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_unary_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctUnary(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_unary_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */

    /* NOT */
    @RepeatedTest(2)
    void wrongNOT(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_NOT_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctNOT(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_NOT_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }
    /* ------- */
}
