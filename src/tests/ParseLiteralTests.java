import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseLiteralTests {
    private TestHelper helper = new TestHelper();

    /* correct literal dcl */
    @RepeatedTest(12)
    void CorrectVarDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/literal/literal_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.literal());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong var dcl */
    @RepeatedTest(4)
    void WrongVarDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/literal/literal_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.literal());
    }
}
