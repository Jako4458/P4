import org.stringtemplate.v4.ST;

import java.util.ArrayList;

public class MinespeakWrittenBuiltinFunctions {
    private final ArrayList<ST> functions;

    public MinespeakWrittenBuiltinFunctions() {
        functions = new ArrayList<>();
        functions.add(minespeakFill(System.lineSeparator()));
        functions.add(minespeakDig(System.lineSeparator()));
    }

    public ST minespeakFill(String lineSeparator) {
        ST st = new ST("func fill(from:vector3, x:num, y:num, z:num, b:block, relative:bool) -> vector3 do<l>" +
                                 "var current:vector3 = <def><l>" +
                                 "if not(x<OP>0 or y<OP>0 or z<OP>0) do<l>" +
                                   "for var i:num=0 until i==x where i+=1 do<l>" +
                                     "for var j:num=0 until j==y where j+=1 do<l>" +
                                       "for var k:num=0 until k==z where k+=1 do<l>" +
                                         "current = from + <TO><l>" +
                                         "setB(current, b, relative)<l>" +
                                  "endfor<l>endfor<l>endfor<l>" +
                                 "endif<l>" +
                                 "return current<l>" +
                               "endfunc<l>");

        st.add("l", lineSeparator);
        st.add("OP", "<=");
        st.add("TO", "<i,j,k>");
        st.add("def", "<0,0,0>");

        return st;
    }

    public ST minespeakDig(String lineSeparator) {
        ST st = new ST("func dig(from:vector3, x:num, y:num, z:num, relative:bool) -> vector3 do<l>" +
                "return fill(from, x, y, z, #air, relative)" +
                "<l>endfunc<l>");

        st.add("l", lineSeparator);

        return st;
    }

    public ArrayList<ST> getFunctions() {
        return functions;
    }
}
