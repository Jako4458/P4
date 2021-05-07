import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseCommentTests {
    private TestHelper helper = new TestHelper();

    /* correct line comment */
    @RepeatedTest(2)
    void CorrectLineComment(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/comment/line_comment_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.prog());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong line comment */
    @RepeatedTest(2)
    void WrongLineComment(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/comment/line_comment_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.prog());
    }
    /* correct block comment */
    @RepeatedTest(2)
    void CorrectBlockComment(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/comment/block_comment_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.prog());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong line comment */
    @RepeatedTest(2)
    void WrongBlockComment(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/comment/block_comment_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.prog());
    }
}
