import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseDoWhileStmntTests {
    private TestHelper helper = new TestHelper();

    /* correct dowhile-stmnt */
    @RepeatedTest(4)
    void CorrectDoWhileStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/do_while_stmnt/do_while_stmnt_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.doWhile());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong while-stmnt */
    @RepeatedTest(3)
    void WrongDoWhileStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/do_while_stmnt/do_while_stmnt_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.doWhile());
    }
}
