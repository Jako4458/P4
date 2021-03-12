import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CharStream charStream = CharStreams.fromFileName("/home/steven/Desktop/P4/parser/test/test1.ms");
        minespeakLexer minespeakLexer = new minespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);
        minespeakParser minespeakParser = new minespeakParser(commonTokenStream);

        ParseTree parseTree = minespeakParser.prog();

        System.out.println("Hej");
    }

}