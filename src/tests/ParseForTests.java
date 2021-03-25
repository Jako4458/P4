import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseForTests {
    private TestHelper helper = new TestHelper();

    /* correct for-stmnt */
    @RepeatedTest(2)
    void CorrectForStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/for_stmnt/for_stmnt_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.forStmnt());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong for-stmnt */
    @RepeatedTest(3)
    void WrongForStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/for_stmnt/for_stmnt_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.forStmnt());
    }
}
