import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class Function {
    private Scope scope;
    private List<TerminalNode> params;
    private ParseTree body;

    public Function(Scope scope, List<TerminalNode> params, ParseTree body) {
        this.scope = scope;
        this.params = params;
        this.body = body;
    }

    public List<TerminalNode> getParams() {
        return this.params;
    }

}
