import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

public class Tests {


    @Test
    public void TestTest() throws Exception{
        CharStream charStream = CharStreams.fromFileName("/home/steven/Desktop/P4/parser/test/test1.ms");
        minespeakLexer minespeakLexer = new minespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);
        minespeakParser minespeakParser = new minespeakParser(commonTokenStream);
        Listener listener = new Listener();
        minespeakParser.addErrorListener(listener);


        minespeakParser.prog();
    }

    @Test
    public void TestTestTest() throws Exception{
        CharStream charStream = CharStreams.fromFileName("/home/steven/Desktop/P4/parser/test/test1.ms");
        minespeakLexer minespeakLexer = new minespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);
        minespeakParser minespeakParser = new minespeakParser(commonTokenStream);
        Listener listener = new Listener();
        minespeakParser.addErrorListener(listener);

    }

    public class Listener implements ANTLRErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
            fail();
        }

        @Override
        public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {

        }

        @Override
        public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {

        }

        @Override
        public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {

        }
    }
}
