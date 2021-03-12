import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class ParseExprTests {
    private TestHelper helper = new TestHelper();

    @RepeatedTest(4)
    void wrongBinary(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_binary_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(3)
    void correctBinary(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_binary_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }

    @RepeatedTest(1)
    void wrongParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_binary_wrong" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @RepeatedTest(2)
    void correctParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_parenthesis_correct" + rI.getCurrentRepetition() + ".ms");
        assertDoesNotThrow(() -> helper.minespeakParser.expr());
    }

    @RepeatedTest(2)
    void missingParenthesis(RepetitionInfo rI) throws IOException {
        helper.setupFromFile("/parser/test/parser/expr/expr_parenthesis_missing" + rI.getCurrentRepetition() + ".ms");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }
}
