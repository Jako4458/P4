import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class ParseFuncTests {
    private TestHelper helper = new TestHelper();

    /* Terminal symbols */
    @RepeatedTest(7)
    void missingTerminalSymbols(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_missing_terminal_symbol" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctTerminalSymbols(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_correct_terminal_symbol" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Params */
    @RepeatedTest(4)
    void wrongParams(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_wrong_params" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(3)
    void correctParams(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_correct_params" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Names */
    @RepeatedTest(3)
    void wrongName(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_wrong_name" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctName(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_correct_name" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Newlines */
    @RepeatedTest(4)
    void wrongNewlines(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_wrong_newlines" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctNewlines(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_correct_newlines" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Return */
    @RepeatedTest(2)
    void wrongReturn(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_wrong_return" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctReturn(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_correct_return" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* NoneFiles */


    @Test
    void returnType() {
        helper.setupFromString("func ID() -> num do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void returnNoType() {
        helper.setupFromString("func ID() -> do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void returnTypeNoArrow() {
        helper.setupFromString("func ID() num do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }
}
