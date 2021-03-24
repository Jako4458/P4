import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.File;
import java.io.IOException;

public class TestHelper {
    public MinespeakLexer minespeakLexer;
    public MinespeakParser minespeakParser;
    static String filePath = new File("").getAbsolutePath();

    public void setupFromFile(String file) throws IOException {
        setup(CharStreams.fromFileName(filePath + file));
    }

    public void setupFromString(String string) {
        setup(CharStreams.fromString(string));
    }

    private void setup(CharStream stream) {
        CharStream charStream = stream;

        minespeakLexer = new MinespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        minespeakParser = new MinespeakParser(commonTokenStream);
        TestsErrorListener listener = new TestsErrorListener();

        minespeakParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        minespeakLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);

        minespeakParser.addErrorListener(listener);
        minespeakLexer.addErrorListener(listener);
    }

    public int getEntryTypeAsInt(Scope scope, String id) {
        return ((TerminalNodeImpl)scope.lookup(id).getType().tree).symbol.getType();
    }

    public void walkTree(ParseTree tree) {
        ScopeListener listener = new ScopeListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
    }

    public String getEnrtyName(Scope scope, String id) {
        return scope.lookup(id).getName();
    }
}
