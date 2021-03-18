import org.antlr.v4.gui.TestRig;
import org.antlr.v4.parse.ScopeParser;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import utils.Function;
import utils.Scope;
import utils.SymEntry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        String testString = "minespeak \n func Test(param1 : num) -> num do \n var n : num = 5 \n if true do \n n = 2 \n else do \n n = 3 \n endif \n endfunc \n\n\n\n closespeak";
        testString = "minespeak \n func Test(param1 : num) -> num do \n var n : num = 5 \n if false do \n var b: num = 1 \n else do \n var a: num = 2 \n endif \n endfunc \n\n\n\n closespeak";
        //testString = "minespeak \n func Test(param1 : num) -> num do \n var n : bool = true \n \n endfunc \n\n\n\n closespeak";
        CharStream charStream = CharStreams.fromString(testString);

        MinespeakLexer minespeakLexer = new MinespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        MinespeakParser minespeakParser = new MinespeakParser(commonTokenStream);

        ParseTree tree = minespeakParser.prog();
        System.out.println(tree.toStringTree(minespeakParser));

        Scope scope = new Scope();
        ScopeVisitor visitor = new ScopeVisitor(scope);
        visitor.visit(tree);

        System.out.println("n is: " + (visitor.getScope().lookup("n") != null ? visitor.getScope().lookup("n").getValue() : "null"));
        System.out.println("a is: " + (visitor.getScope().lookup("a") != null ? visitor.getScope().lookup("a").getValue() : "null"));
        System.out.println("b is: " + (visitor.getScope().lookup("b") != null ? visitor.getScope().lookup("b").getValue() : "null"));
    }

}