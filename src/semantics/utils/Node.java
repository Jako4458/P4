import java.util.ArrayList;
import java.util.List;

public class Node implements TreeNode {
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private int scope;
    private SymbolTable table;
    private int index = 0;

    public Node(int scope, Node parent) {
        this.scope = scope;
        this.parent = parent;
    }

    public Node startScope() {
        children.add(new Node(this.scope + 1, this));
        return children.get(index++);
    }

    public Node getParent() {
        return this.parent;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public Node getChild(int index) {
        return this.children.get(index);
    }

    public SymbolTable getTable() {
        return this.table;
    }

    public int getScope() {
        return this.scope;
    }
}

