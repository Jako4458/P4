import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.Test;

public class ParseFuncTests {
    private TestHelper helper = new TestHelper();

    @Test
    void TestValidFunc() {
        helper.setupFromString("func ID() do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void TestInvalidFunc() {
        helper.setupFromString("function ID() do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }


}
