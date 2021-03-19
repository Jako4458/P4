package utils;


public class Type {
    public static final Type INVALID = new Type(null);
    public static final int BOOLEAN = MinespeakParser.BOOLEAN;


    public final ParseTree tree;



    public Type(ParseTree tree) { this.tree = tree; }
}
