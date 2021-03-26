import Logging.Logger;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //String testString = "minespeak \n func Test(param1 : num) -> num do \n var n : num = 5 \n if true do \n n = 2 \n else do \n n = 3 \n endif \n endfunc \n\n\n\n closespeak";
        //testString = "minespeak \n func Test(param1 : num) -> num do \n var n : num = 5 \n if false do \n var b: num = 1 \n else do \n var a: num = 2 \n endif \n endfunc \n\n\n\n closespeak";
        //testString = "minespeak \n func Test(param1 : num) -> num do \n var n : bool = true \n \n endfunc \n\n\n\n closespeak";

        CharStream charStream = CharStreams.fromFileName("F:\\git_projects\\P4\\src\\testString.ms");

        //CharStream charStream = CharStreams.fromString(testString);

        Logger.shared.setSourceProg(charStream.toString().split(System.getProperty("line.separator")));

        MinespeakLexer minespeakLexer = new MinespeakLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(minespeakLexer);

        MinespeakParser minespeakParser = new MinespeakParser(commonTokenStream);

        ParseTree tree = minespeakParser.prog();
        System.out.println(tree.toStringTree(minespeakParser));

        ScopeListener listener = new ScopeListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);

        Logger.shared.print();
        //System.out.println(commonTokenStream.get(25, 29));
//        System.out.println(tree);
//
//        System.out.println(Type.resultTypes);

        /*Scope scope = new Scope();
        ScopeVisitorDepre visitor = new ScopeVisitorDepre(scope);
        visitor.visit(tree);


        System.out.println("n is: " + (visitor.getScope().lookup("n") != null ? visitor.getScope().lookup("n").getValue() : "null"));
        System.out.println("a is: " + (visitor.getScope().lookup("a") != null ? visitor.getScope().lookup("a").getValue() : "null"));
        System.out.println("b is: " + (visitor.getScope().lookup("b") != null ? visitor.getScope().lookup("b").getValue() : "null"));
        
        System.out.println("n is: " + visitor.getScope().lookup("n").getValue());
*/
    }

}