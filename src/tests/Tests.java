
import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

public class Tests {
    public TestHelper helper = new TestHelper();

    @Test
    public void TestTest() throws Exception{
        helper.setupFromFile("/parser/test/test1.ms");
        helper.minespeakParser.prog();
    }
}
