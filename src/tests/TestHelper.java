import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public void walkTreeWithListener(ParseTree tree, MinespeakBaseListener listener) {
        ParseTreeWalker.DEFAULT.walk(listener, tree);
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

    public static String getSymbolFromInt(int symbol) {
        return (MinespeakParser.VOCABULARY.getLiteralName(symbol)).replaceAll("'", "");
    }

    /*public static String getRepresentativeSymbol(String type) {
        Vocabulary vocab = MinespeakParser.VOCABULARY;
        switch (type) {
            case vocab.getLiteralName(Type.BOOL):     return "true";
            case MinespeakParser.VOCABULARY.getLiteralName(Type.NUM):      return "1";
            case MinespeakParser.VOCABULARY.getLiteralName(Type.BLOCK):    return "#ACACIA_BUTTON";
            case MinespeakParser.VOCABULARY.getLiteralName(Type.STRING):   return "\"test\"";
            case MinespeakParser.VOCABULARY.getLiteralName(Type.VECTOR2):  return "<1, 1>";
            case MinespeakParser.VOCABULARY.getLiteralName(Type.VECTOR3):  return "<1, 1, 1>";
            default:            return "\"error\"";
        }
    }*/

    public static String getRepresentativeSymbol(String type) {
        return TestHelper.repSymbols.getOrDefault(type, "\"error\"");
    }
    public static String getRepresentativeSymbol(int type) {
        return TestHelper.repSymbols.getOrDefault(getSymbolFromInt(type), "\"error\"");
    }


    static HashMap<String, String> repSymbols = new HashMap<>() {{
        put(TestHelper.getSymbolFromInt(MinespeakParser.NUM), "1");
        put(TestHelper.getSymbolFromInt(MinespeakParser.BLOCK), "#ACACIA_BUTTON");
        put(TestHelper.getSymbolFromInt(MinespeakParser.STRING), "\"test\"");
        put(TestHelper.getSymbolFromInt(MinespeakParser.VECTOR2), "<1, 1>");
        put(TestHelper.getSymbolFromInt(MinespeakParser.VECTOR3), "<1, 1, 1>");
        put(TestHelper.getSymbolFromInt(MinespeakParser.BOOL), "true");
    }};

}
