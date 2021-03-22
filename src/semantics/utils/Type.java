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
    public static final Type _vector2 = new Type(new TerminalNodeImpl(new CommonToken(VECTOR2, "vector2")));
    public static final Type _vector3 = new Type(new TerminalNodeImpl(new CommonToken(VECTOR3, "vector3")));

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
        Set<Integer> vectorTypes = new HashSet<Integer>() {{
            add(MinespeakParser.VECTOR2);
            add(MinespeakParser.VECTOR3);
        }};

        for (int op : arithmeticOps) {
            resultTypes.put(opKey(NUM, op, NUM), NUM);
        }

        for (int op : relationalOps) {
            resultTypes.put(opKey(BOOL, op, BOOL), BOOL);
        }

        for (int op : vectorOps) {
            for (int type : vectorTypes) {
                resultTypes.put(opKey(type, op, type), type);
            }
        }

        // Multiplication of vector by scalar
        for (int type : vectorTypes) {
            resultTypes.put(opKey(NUM, MinespeakParser.TIMES, type), type);
            resultTypes.put(opKey(type, MinespeakParser.TIMES, NUM), type);
        }

    }

    public Type(ParseTree tree) { this.tree = tree; }

    public static boolean isValidOp(int left, int op, int right) {
        return Type.resultTypes.get(Type.opKey(left, op, right)) != null;
    }

    public static Type inferType(int left, int op, int right) {
        return getTypeFromInt(Type.resultTypes.get(Type.opKey(left, op, right)));
    }

    public static Type getTypeFromInt(int val) {
        Type type;
        switch (val) {
            case Type.BOOL:
                type = Type._bool; break;
            case Type.NUM:
                type = Type._num; break;
            case Type.BLOCK:
                type = Type._block; break;
            case Type.STRING:
                type = Type._string; break;
            case Type.VECTOR2:
                type = Type._vector2; break;
            case Type.VECTOR3:
                type = Type._vector3; break;
            default:
                type = Type.INVALID;
        }
        return type;
    }

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