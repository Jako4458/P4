import Logging.ErrorLog;
import Logging.Logger;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static Configuration config;



    public static void main(String[] args) {
        // Configure the compiler through the compiler arguments.
        Configuration config = new Configuration(args);

        // Lexing
        CommonTokenStream tokenStream = lex(config.source_file.toString());

        // Parsing
        ParseTree parseTree = parse(tokenStream);

        // Syntax analysis
        syntax_analysis(parseTree);

        // Code gen
        generate_code(parseTree);


        // Dump all the logs
        Logger.shared.print();
    }

    private static CommonTokenStream lex(String file) {
        if (file == null)
            return null;

        CommonTokenStream stream = null;

        try {
            CharStream cstream = CharStreams.fromFileName(file);
            MinespeakLexer minespeakLexer = new MinespeakLexer(cstream);
            stream = new CommonTokenStream(minespeakLexer);
        } catch (IOException e) {
            Logger.shared.add(new ErrorLog(String.format("Unable to open file: %s", file), 0, 0));
        }

        return stream;
    }

    private static ParseTree parse(CommonTokenStream tstream) {
        if (tstream == null)
            return null;

        MinespeakParser parser = new MinespeakParser(tstream);
        ParseErrorListener parseErrorListener = new ParseErrorListener();
        parser.addErrorListener(parseErrorListener);
        ParseTree tree = parser.prog();

        if (parseErrorListener.getErrorFound()) {
            return null;
        }

        return tree;
    }

    private static void syntax_analysis(ParseTree tree) {
        if (tree == null)
            return;

        SignatureWalker walker = new SignatureWalker();
        walker.visit(tree);
        ScopeListener scopeListener = new ScopeListener(walker.functionSignatures);
        ParseTreeWalker.DEFAULT.walk(scopeListener, tree);

        UnassignedVariableListener unassignedVariableListener = new UnassignedVariableListener();
        ParseTreeWalker.DEFAULT.walk(unassignedVariableListener, tree);
    }

    public static void generate_code(ParseTree tree) {
        //TODO: Codegen
    }
    
}


class Configuration {
    public final File source_file;

    public Configuration(String[] args) {
        source_file = new File(args[0]);
    }
}