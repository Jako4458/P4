import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseWhileStmntTests {
    private TestHelper helper = new TestHelper();

    /* correct while-stmnt */
    @RepeatedTest(4)
    void CorrectWhileStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/while_stmnt/while_stmnt_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.whileStmnt());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong while-stmnt */
    @RepeatedTest(3)
    void WrongWhileStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/stmnt/while_stmnt/while_stmnt_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.whileStmnt());
    }
}
