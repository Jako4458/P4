import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseForeachTests {
    private TestHelper helper = new TestHelper();

    /* correct foreach-stmnt */
    @RepeatedTest(4)
    void CorrectForeachStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/foreach_stmnt/foreach_stmnt_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.foreach());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong foreach-stmnt */
    @RepeatedTest(2)
    void WrongForeachStmnt(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/foreach_stmnt/foreach_stmnt_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.foreach());
    }
}
