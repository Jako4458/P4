import logging.logs.ErrorLog;
import logging.Logger;
import logging.logs.Log;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Main {
    private static Configuration config;
    private static Map<String, FuncEntry> functionSignatures;
    public static Setup setup;

    public static void main(String[] args) {
        // Configure the compiler through the compiler arguments.
        config = new Configuration(args);
        setup = (new SetupReader()).readSetupJSON();

        // Compile the file
        compile();

        // Dump all the logs
        Logger.shared.print();
    }

    private static void compile() {
        // Building builtin functions
        FileManager fManager = new FileManager(setup.outputPath);
        fManager.buildBeforeCodeGen();

        // Lexing
        System.out.println("Lexing...");
        CommonTokenStream tokenStream = lex(config.source_file.toString());
        if (checkLoggerIsNotOK())
            return;

        // Parsing
        System.out.println("Parsing...");
        ParseTree parseTree = parse(tokenStream);
        if (parseTree == null)
            return;

        // Semantic analysis
        System.out.println("Semantics...");
        semanticAnalysis(parseTree);
        if (checkLoggerIsNotOK())
            return;

        // Code gen
        System.out.println("Code gene...");
        ArrayList<Template> output = codeGeneration(parseTree);
        if (checkLoggerIsNotOK())
            return;

        // Outputting to files
        System.out.println("Making files...");
        makeFiles(fManager, output);

    }

    private static boolean checkLoggerIsNotOK() {
        if(setup.pedantic) {
            return Logger.shared.containsWarnings() || Logger.shared.containsErrors();
        }
        return Logger.shared.containsErrors();
    }

    private static boolean makeFiles(FileManager fManager, ArrayList<Template> output) {
        return fManager.buildCodeGen(output);
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
        Main.functionSignatures = walker.functionSignatures;
        ScopeListener scopeListener = new ScopeListener(walker.functionSignatures, BuiltinFuncs.paramMap);
        ParseTreeWalker.DEFAULT.walk(scopeListener, tree);

        UnassignedVariableListener unassignedVariableListener = new UnassignedVariableListener();
        ParseTreeWalker.DEFAULT.walk(unassignedVariableListener, tree);

        InfiniteLoopDetectionVisitor infiniteLoopDetectionVisitor = new InfiniteLoopDetectionVisitor();
        infiniteLoopDetectionVisitor.visit(tree);

//        InfiniteLoopDetectionListener infiniteLoopDetectionListener = new InfiniteLoopDetectionListener();
//        ParseTreeWalker.DEFAULT.walk(infiniteLoopDetectionListener, tree);
    }

    public static ArrayList<Template> codeGeneration(ParseTree tree) {
        CodeGenVisitor codeGenVisitor = new CodeGenVisitor(Main.functionSignatures, BuiltinFuncs.paramMap);
        return codeGenVisitor.visit(tree);
    }
    
}


class Configuration {
    public File source_file;
    public String filePath = System.getProperty("user.dir");

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




































