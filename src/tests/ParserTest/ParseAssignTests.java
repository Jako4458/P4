import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseAssignTests {
    private TestHelper helper = new TestHelper();

    /* correct assign */
    @RepeatedTest(2)
    void CorrectAssign(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/assign/assign_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.assign());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong assign */
    @RepeatedTest(2)
    void WrongAssign(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/assign/assign_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.assign());
    }
}
