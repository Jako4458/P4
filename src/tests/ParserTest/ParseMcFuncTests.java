import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseMcFuncTests {
    private TestHelper helper = new TestHelper();

    /* correct mcFunc */
    @RepeatedTest(4)
    void CorrectMcFunc(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/mcFunc/mcFunc_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.mcFunc());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong mcFunc */
    @RepeatedTest(2)
    void WrongMcFunc(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/mcFunc/mcFunc_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.mcFunc());
    }
}
