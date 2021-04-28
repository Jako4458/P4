import java.util.HashMap;

public class SymbolConverter {

    static private final HashMap<Integer, String> symbols = new HashMap<Integer, String>() {{
        put(MinespeakParser.ADD, "+");
        put(MinespeakParser.SUB, "-");
        put(MinespeakParser.TIMES, "*");
        put(MinespeakParser.DIV, "/");
        put(MinespeakParser.MOD, "%");
        put(MinespeakParser.EQUAL, "==");
        put(MinespeakParser.NOTEQUAL, "!=");
        put(MinespeakParser.ASSIGN, "=");
        put(MinespeakParser.MODASSIGN, "%=");
        put(MinespeakParser.MULTASSIGN, "*=");
        put(MinespeakParser.DIVASSIGN, "/=");
        put(MinespeakParser.ADDASSIGN, "+=");
        put(MinespeakParser.SUBASSIGN, "-=");
    }};

    static public String getSymbol(int symval) {
        return symbols.get(symval);
    }

}
