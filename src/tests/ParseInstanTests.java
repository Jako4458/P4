import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParseInstanTests {
    private TestHelper helper = new TestHelper();

    /* correct var instan */
    @RepeatedTest(3)
    void CorrectVarInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_var_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong var instan */
    @RepeatedTest(4)
    void WrongVarInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_const_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }

    /* correct const instan */
    @RepeatedTest(3)
    void CorrectConstInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_const_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong const instan */
    @RepeatedTest(4)
    void WrongConstInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_const_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }

    /* correct colon instan */
    @RepeatedTest(1)
    void CorrectColonInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_colon_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong colon instan */
    @RepeatedTest(2)
    void WrongColonInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_colon_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }

    /* correct id instan */
    @RepeatedTest(5)
    void CorrectIdInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_id_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong id instan */
    @RepeatedTest(2)
    void WrongIdInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_id_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }

    /* correct order instan */
    @RepeatedTest(1)
    void CorrectOrderInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_order_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong order instan */
    @RepeatedTest(3)
    void WrongOrderInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_order_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }


    /* correct assign instan */
    @RepeatedTest(3)
    void CorrectAssignInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_assign_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong assign instan */
    @RepeatedTest(3)
    void WrongAssignInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_assign_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }

    /* correct assign instan */
    @RepeatedTest(3)
    void CorrectArrayInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_array_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.instan());
        assertEquals(helper.minespeakParser.getCurrentToken().getType(), Token.EOF);
    }

    /* wrong assign instan */
    @RepeatedTest(2)
    void WrongArrayInstan(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/instan/instan_array_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.instan());
    }
}
