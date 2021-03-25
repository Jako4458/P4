import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseFactorTests {
    private TestHelper helper = new TestHelper();

    /* correct factor */
    @RepeatedTest(5)
    void CorrectFactor(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/factor/factor_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.factor());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }
}
