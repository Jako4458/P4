import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.File;
import java.io.IOException;

public class TestHelper {
    public MinespeakLexer minespeakLexer;
    public MinespeakParser minespeakParser;
    static String filePath = new File("").getAbsolutePath();
    public Vocabulary vocabulary;
    private ScopeListener listener;

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
        this.vocabulary = minespeakParser.getVocabulary();
        TestsErrorListener listener = new TestsErrorListener();

        minespeakParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        minespeakLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);

        minespeakParser.addErrorListener(listener);
        minespeakLexer.addErrorListener(listener);
    }

    public int getEntryTypeAsInt(Scope scope, String id) {
        return ((TerminalNodeImpl)scope.lookup(id).getType().tree).symbol.getType();
    }

    public boolean entryExists(Scope scope, String id) {
        return scope.lookup(id) != null;
    }

    public void walkTree(ParseTree tree) {
        SignatureWalker sigWalker = new SignatureWalker();
        sigWalker.visit(tree);
        listener = new ScopeListener(sigWalker.functionSignatures);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        ParseTreeWalker.DEFAULT.walk(new UnassignedVariableListener(), tree);
    }

    public <T> void walkTree(ParseTree tree, MinespeakBaseVisitor<T> visitor) {
        visitor.visit(tree);
    }

    public String getEntryName(Scope scope, String id) {
        return scope.lookup(id).getName();
    }

    public static String getTypeName(int type) {
        return MinespeakParser.VOCABULARY.getLiteralName(type);
    }

    public ScopeListener getListener() {
        return this.listener;
    }
}
