import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseFuncCallTests {
    private TestHelper helper = new TestHelper();

    /* Call */
    @RepeatedTest(4)
    void wrongCall(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/funcCall/func_call_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.funcCall());
    }

    @RepeatedTest(3)
    void correctCall(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/funcCall/func_call_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.funcCall());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
    /* ------- */
}
