import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import java.io.IOException;

public class ParseDclTests {
    private TestHelper helper = new TestHelper();

    /* correct var dcl */
    @RepeatedTest(3)
    void CorrectVarDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_var_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.dcls());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong var dcl */
    @RepeatedTest(4)
    void WrongVarDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_const_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.dcls());
    }

    /* correct const dcl */
    @RepeatedTest(3)
    void CorrectConstDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_const_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.dcls());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong const dcl */
    @RepeatedTest(4)
    void WrongConstDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_const_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.dcls());
    }

    /* correct colon dcl */
    @RepeatedTest(1)
    void CorrectColonDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_colon_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.dcls());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong colon dcl */
    @RepeatedTest(2)
    void WrongColonDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_colon_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.dcls());
    }

    /* correct id dcl */
    @RepeatedTest(5)
    void CorrectIdDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_id_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.dcls());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong id dcl */
    @RepeatedTest(2)
    void WrongIdDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_id_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.dcls());
    }

    /* correct order dcl */
    @RepeatedTest(1)
    void CorrectOrderDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_order_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.dcls());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong order dcl */
    @RepeatedTest(3)
    void WrongOrderDcl(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/dcl/dcl_order_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.dcls());
    }
}
