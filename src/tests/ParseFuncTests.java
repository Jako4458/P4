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
    void wrongTerminalSymbols(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_terminal_symbol_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctTerminalSymbols(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_terminal_symbol_correct" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Params */
    @RepeatedTest(4)
    void wrongParams(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_params_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(3)
    void correctParams(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_params_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Names */
    @RepeatedTest(3)
    void wrongName(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_name_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctName(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_name_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Newlines */
    @RepeatedTest(4)
    void wrongNewlines(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_newlines_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctNewlines(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_newlines_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */

    /* Return */
    @RepeatedTest(2)
    void wrongReturn(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_return_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctReturn(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/func/func_return_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }
    /* ------- */
}
