import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class TestHelper {
    public minespeakLexer minespeakLexer;
    public minespeakParser minespeakParser;

    public void setupFromFile(String file) throws IOException {
        setup(CharStreams.fromFileName(file));
    }

    public void setupFromString(String string){
        setup(CharStreams.fromString(string));
    }

    private void setup(CharStream stream) {
        CharStream charStream = stream;

        minespeakLexer = new minespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        minespeakParser = new minespeakParser(commonTokenStream);
        TestsErrorListener listener = new TestsErrorListener();

        minespeakParser.addErrorListener(listener);
        minespeakLexer.addErrorListener(listener);
    }

}
