import exceptions.SyntaxErrorException;
import org.antlr.runtime.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleParseTests {
    private TestHelper helper = new TestHelper();

    /* Test of example program Pyramid */
    @Test
    void PyramidTest() throws IOException {
        helper.setupFromFile("/Examples/Pyramid.ms");
        assertDoesNotThrow(() -> helper.minespeakParser.prog());
    }

    /* Test of example program Castle */
    @Test
    void CastleTest() throws IOException {
        helper.setupFromFile("/Examples/Castle.ms");
        assertDoesNotThrow(() -> helper.minespeakParser.prog());
    }
}
