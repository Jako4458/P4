import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class Function {
    private Scope scope;
    private List<String> params;
    private Type retType;
    private ParseTree body;
    private String name;

    public Function(Scope scope, List<String> params, Type retType, ParseTree body) {
        this.scope = scope;
        this.params = params;
        this.retType = retType;
        this.body = body;
    }

    public Function(MinespeakParser.FuncContext ctx) {
        
    }

    public List<String> getParams() {
        return this.params;
    }

    public String getName() {
        return this.name;
    }
}
