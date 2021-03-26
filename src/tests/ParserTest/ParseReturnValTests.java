import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseReturnValTests {
    private TestHelper helper = new TestHelper();

    /* correct returnVal */
    @RepeatedTest(5)
    void CorrectReturnVal(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/returnVal/returnVal_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.retVal());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong returnVal */
    @RepeatedTest(1)
    void WrongReturnVal(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/returnVal/returnVal_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.retVal());
    }

    /* wrong returnVal as not whole value-expr/-stmnt is captured as the return val and is stopped after first valid */
    @RepeatedTest(3)
    void WrongReturnValCapture(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/returnVal/returnVal_wrong_not_all_capture" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.retVal());
        assertNotEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
}
