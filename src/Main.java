import Logging.Logger;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = new File("").getAbsolutePath();
        CharStream charStream = CharStreams.fromFileName(filePath + "\\src\\testString.ms");

        Logger.shared.setSourceProg(charStream.toString().split(System.getProperty("line.separator")));

        MinespeakLexer minespeakLexer = new MinespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        MinespeakParser minespeakParser = new MinespeakParser(commonTokenStream);
        ParseErrorListener parseErrorListener = new ParseErrorListener();
        minespeakParser.addErrorListener(parseErrorListener);

        ParseTree tree = minespeakParser.prog();
        System.out.println(tree.toStringTree(minespeakParser));

        if (parseErrorListener.getErrorFound()) {
            return;
        }

        SignatureWalker walker = new SignatureWalker();
        walker.visit(tree);
        ScopeListener scopeListener = new ScopeListener(walker.functionSignatures);
        ParseTreeWalker.DEFAULT.walk(scopeListener, tree);

        UnassignedVariableListener unassignedVaribleListener = new UnassignedVariableListener();
        ParseTreeWalker.DEFAULT.walk(unassignedVaribleListener, tree);

        Logger.shared.print();

        CodeGenVisitor codeGenVisitor = new CodeGenVisitor(walker.functionSignatures);
        codeGenVisitor.visit(tree);
    }

}