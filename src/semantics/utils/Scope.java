import java.util.HashMap;

public class Scope {
    private Scope parent;
    private HashMap<String, SymEntry> variables;
    private boolean isFunction;
    private boolean nextFuncIsMCFunc;

    // Used for global scope
    public Scope() {
        this(null);
    }

    // Used for any scope
    public Scope(Scope parent) {
        this.parent = parent;
        this.isFunction = false;
        this.variables = new HashMap<>();
    }

    public Scope(Scope parent, boolean isFunction) {
        this.parent = parent;
        this.isFunction = isFunction;
        this.variables = new HashMap<>();
    }

    // Not good
    public void addVariable(String key, SymEntry var) {
        variables.putIfAbsent(key, var);
    }

    public SymEntry lookup(String key) {
        return this.variables.getOrDefault(key, null);
    }

    public void reAssign(String id, SymEntry value) {
        variables.replace(id, value);
    }

    public Scope getParent() {
        return this.parent;
    }

    public boolean isFunction() {
        return this.isFunction;
    }

    public void MarkNextFuncAsMCFunc() {
        this.nextFuncIsMCFunc = true;
    }
}
