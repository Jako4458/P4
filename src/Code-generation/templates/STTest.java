
import org.stringtemplate.v4.*;

public class STTest {

    public STTest(String name){
        ST hello = new ST("Hello, <name>!");
        hello.add("name", name);

        String output = hello.render();
    }


}
