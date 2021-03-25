import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseIfStmntTests {
    private TestHelper helper = new TestHelper();

    /* correct if-stmnt */
    @RepeatedTest(6)
    void CorrectAssign(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/if_stmnt/if_stmnt_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.ifStmnt());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong if-stmnt */
    @RepeatedTest(4)
    void WrongAssign(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/if_stmnt/if_stmnt_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.ifStmnt());
    }
}
