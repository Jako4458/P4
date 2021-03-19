import org.antlr.v4.runtime.tree.ParseTree;

public class Type {
    public static final Type INVALID = new Type(null);
    public static final int BOOLEAN = MinespeakParser.BOOL;


    public final ParseTree tree;



    public Type(ParseTree tree) { this.tree = tree; }
}
