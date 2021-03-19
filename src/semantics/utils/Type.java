import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class Type {
    public static final Type INVALID = new Type(null);

    public static final int BOOL = MinespeakParser.BOOL;
    public static final int NUM = MinespeakParser.NUM;
    public static final int STRING = MinespeakParser.STRING;
    public static final int BLOCK = MinespeakParser.BLOCK;

    public static final int ARRAY = MinespeakParser.ARRAY;
    public static final int VECTOR2 = MinespeakParser.VECTOR2;
    public static final int VECTOR3 = MinespeakParser.VECTOR3;


    public static final Type _bool = new Type(new TerminalNodeImpl(new CommonToken(BOOL, "bool")));
    public static final Type _num = new Type(new TerminalNodeImpl(new CommonToken(NUM, "num")));
    public static final Type _string = new Type(new TerminalNodeImpl(new CommonToken(STRING, "string")));
    public static final Type _block = new Type(new TerminalNodeImpl(new CommonToken(BLOCK, "block")));


    public final ParseTree tree;



    public Type(ParseTree tree) { this.tree = tree; }
}
