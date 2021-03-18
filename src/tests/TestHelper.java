import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;

import java.io.File;
import java.io.IOException;

public class TestHelper {
    public minespeakLexer minespeakLexer;
    public minespeakParser minespeakParser;
    static String filePath = new File("").getAbsolutePath();

    public void setupFromFile(String file) throws IOException {
        setup(CharStreams.fromFileName(filePath + file));
    }

    public void setupFromString(String string) {
        setup(CharStreams.fromString(string));
    }

    private void setup(CharStream stream) {
        CharStream charStream = stream;

        minespeakLexer = new minespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        minespeakParser = new minespeakParser(commonTokenStream);
        TestsErrorListener listener = new TestsErrorListener();

        minespeakParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        minespeakLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);

        minespeakParser.addErrorListener(listener);
        minespeakLexer.addErrorListener(listener);
    }

}
