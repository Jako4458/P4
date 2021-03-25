import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseLiteralTests {
    private TestHelper helper = new TestHelper();

    /* correct literal */
    @RepeatedTest(14)
    void CorrectLiteral(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/literal/literal_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.literal());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong literal */
    @RepeatedTest(7)
    void WrongLiteral(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/literal/literal_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.literal());
    }

    /* wrong only one literal */
    @RepeatedTest(1)
    void WrongOnlyOneLiteral(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/literal/literal_one_wrong" + rI.getCurrentRepetition() + ".ms");
        helper.minespeakParser.literal();
        assertNotEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
}
