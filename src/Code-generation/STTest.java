import org.stringtemplate.v4.*;

public class STTest {

    public STTest(){
        ST hello = new ST("Hello, <name>!");
        hello.add("name", "Christian");

        String output = hello.render();
        System.out.println(output);
    }


}
