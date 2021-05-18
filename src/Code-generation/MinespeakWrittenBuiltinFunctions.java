import org.stringtemplate.v4.ST;

import java.util.ArrayList;

public class MinespeakWrittenBuiltinFunctions {
    private ArrayList<ST> functions;

    public MinespeakWrittenBuiltinFunctions() {
        functions = new ArrayList<>();
        functions.add(minespeakFill(System.lineSeparator()));
    }

    public ST minespeakFill(String lineSeperator) {
        ST st = new ST("func fill(from:vector3, x:num, y:num, z:num, b:block, relative:bool) do<lineSeperator>" +
                               "if not(x<OP>0 or y<OP>0 or z<OP>0) do<lineSeperator>" +
                               "for var i:num=0 until i==x where i+=1 do<lineSeperator>" +
                               "for var j:num=0 until j==y where j+=1 do<lineSeperator>" +
                               "for var k:num=0 until k==z where k+=1 do<lineSeperator>" +
                               "setB(from + <TO>, b, relative)<lineSeperator>" +
                               "endfor<lineSeperator>endfor<lineSeperator>endfor<lineSeperator>" +
                               "endif" +
                               "<lineSeperator>endfunc<lineSeperator>");

        st.add("lineSeperator", lineSeperator);
        st.add("OP", "<=");
        st.add("TO", "<i,j,k>");

        return st;
    }

    public ArrayList<ST> getFunctions() {
        return functions;
    }
}
