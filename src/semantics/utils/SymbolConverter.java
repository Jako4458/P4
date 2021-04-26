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
    }};

    static public String getSymbol(int symval) {
        return symbols.get(symval);
    }

}
