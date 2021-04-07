package templates;

import org.stringtemplate.v4.ST;

public class MCStatementST {

    public MCStatementST(String command){
        ST hello = new ST("<Command>");
        hello.add("Command", command);

        String output = hello.render();
        System.out.println(output);
    }


}
