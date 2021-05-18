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
        ST st = new ST("func fill(from:vector3, x:num, y:num, z:num, b:block, relative:bool) do<lineSeparator>" +
                               "if not(x<OP>0 or y<OP>0 or z<OP>0) do<lineSeparator>" +
                               "for var i:num=0 until i==x where i+=1 do<lineSeparator>" +
                               "for var j:num=0 until j==y where j+=1 do<lineSeparator>" +
                               "for var k:num=0 until k==z where k+=1 do<lineSeparator>" +
                               "setB(from + <TO>, b, relative)<lineSeparator>" +
                               "endfor<lineSeparator>endfor<lineSeparator>endfor<lineSeparator>" +
                               "endif" +
                               "<lineSeparator>endfunc<lineSeparator>");

        st.add("lineSeparator", lineSeparator);
        st.add("OP", "<=");
        st.add("TO", "<i,j,k>");

        return st;
    }

    public ST minespeakDig(String lineSeparator) {
        ST st = new ST("func dig(from:vector3, x:num, y:num, z:num, relative:bool) do<lineSeparator>" +
                "fill(from, x, y, z, #air, relative)" +
                "<lineSeparator>endfunc<lineSeparator>");

        st.add("lineSeparator", lineSeparator);

        return st;
    }

    public ArrayList<ST> getFunctions() {
        return functions;
    }
}
