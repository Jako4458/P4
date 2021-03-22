import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Represents possible type trees
 *
 *  Used only in expressions.
 *  Same method as that used for Mantra by Terrence Parr, rewrtitten here for the Minespeak language
 *  https://github.com/mantra/compiler/blob/master/src/java/mantra/symbols/Type.java
 */

public class Type {
    public static final Type INVALID = new Type(null);

    public static final int BOOL = MinespeakParser.BOOL;
    public static final int NUM = MinespeakParser.NUM;
    public static final int STRING = MinespeakParser.STRING;
    public static final int BLOCK = MinespeakParser.BLOCK;

    public static final int ARRAY = MinespeakParser.ARRAY;
    public static final int VECTOR2 = MinespeakParser.VECTOR2;
    public static final int VECTOR3 = MinespeakParser.VECTOR3;

    /** Creates
     *
     */
    public static final Type _bool = new Type(new TerminalNodeImpl(new CommonToken(BOOL, "bool")));
    public static final Type _num = new Type(new TerminalNodeImpl(new CommonToken(NUM, "num")));
    public static final Type _string = new Type(new TerminalNodeImpl(new CommonToken(STRING, "string")));
    public static final Type _block = new Type(new TerminalNodeImpl(new CommonToken(BLOCK, "block")));

    public final ParseTree tree;

    public static final Map<Long, Integer> resultTypes = new HashMap<Long, Integer>();

    /*
    expr
    returns [Type type]
        : op=(NOT | SUB)? factor                                # NotNegFac
        | <assoc=right> expr POW expr                           # Pow
        | expr op=(TIMES | DIV | MOD) expr                      # MulDivMod
        | expr op=(ADD | SUB) expr                              # AddSub
        | expr op=(LESSER | GREATER | LESSEQ | GREATEQ) expr    # relations
        | expr op=(EQUAL | NOTEQUAL) expr                       # equality
        | expr AND expr                                         # and
        | expr OR expr                                          # or
        ;

     */
    static {
        // make a table to handle some larger amount of expressions, but not all 😈
        Set<Integer> arithmeticOps = new HashSet<Integer>() {{
            add(MinespeakParser.ADD);
            add(MinespeakParser.SUB);
            add(MinespeakParser.TIMES);
            add(MinespeakParser.DIV);
            add(MinespeakParser.POW);
            add(MinespeakParser.MOD);
        }};
        Set<Integer> relationalOps = new HashSet<Integer>() {{
            add(MinespeakParser.LESSER);
            add(MinespeakParser.GREATER);
            add(MinespeakParser.LESSEQ);
            add(MinespeakParser.GREATEQ);
            add(MinespeakParser.EQUAL);
            add(MinespeakParser.NOTEQUAL);
            add(MinespeakParser.AND);
            add(MinespeakParser.OR);

        }};
        Set<Integer> vectorOps = new HashSet<Integer>() {{
            add(MinespeakParser.ADD);
            add(MinespeakParser.SUB);
        }};

        for (int op : arithmeticOps) {
            resultTypes.put(opKey(NUM, op, NUM), NUM);
        }

    }

    public Type(ParseTree tree) { this.tree = tree; }

    @Override
    public String toString() {
        return tree.getText();
    }

    public static long opKey(int left, int op, int right) {
        return ((long)left) << 32 | ((long)op) << 16 | right;
    }

    public static Type resultType(Type left, int opTokenType, Type right){
        return INVALID;
    }

    public static Type promote(Type from, int opTokenType, Type to){
        return INVALID;
    }

    public static Type canAssign(Type from, Type to){
        return INVALID;
    }
}
