import logging.logs.ErrorLog;
import logging.Logger;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.io.File;
import java.io.IOException;

public class Main {

    private static Configuration config;

    public static void main(String[] args) {
        // Configure the compiler through the compiler arguments.
        Configuration config = new Configuration(args);

        // Lexing
        System.out.println("Lexing...");
        CommonTokenStream tokenStream = lex(config.source_file.toString());

        // Parsing
        System.out.println("Parsing...");
        ParseTree parseTree = parse(tokenStream);

        // Semantic analysis
        System.out.println("Semantics...");
        semanticAnalysis(parseTree);

        // Code gen
        System.out.println("Code gene...");
        codeGeneration(parseTree);

        // Dump all the logs
        Logger.shared.print();
    }

    private static CommonTokenStream lex(String file) {
        if (file == null)
            return null;

        CommonTokenStream stream = null;

        try {
            CharStream cstream = CharStreams.fromFileName(file);
            Logger.shared.setSourceProg(cstream.toString().split(System.getProperty("line.separator")));
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


    private static void semanticAnalysis(ParseTree tree) {
        if (tree == null)
            return;

        SignatureWalker walker = new SignatureWalker();
        walker.visit(tree);
        ScopeListener scopeListener = new ScopeListener(walker.functionSignatures);
        ParseTreeWalker.DEFAULT.walk(scopeListener, tree);

        UnassignedVariableListener unassignedVariableListener = new UnassignedVariableListener();
        ParseTreeWalker.DEFAULT.walk(unassignedVariableListener, tree);

        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
        ParseTreeWalker.DEFAULT.walk(infiniteLoopDetectionListener, tree);
    }

    public static void codeGeneration(ParseTree tree) {
        //TODO: Codegen
    }
    
}


class Configuration {
    public File source_file;

    public Configuration(String[] args) {
        parse_args(args);
    }

    private void parse_args(String[] args) {
        long ac = args.length;

        if (ac == 0) {
            throw new RuntimeException("No arguments supplied");
        }

        if (ac == 1) {
            this.source_file = new File(args[0]);
        }
    }
}




































