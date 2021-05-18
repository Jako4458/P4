import java.util.HashMap;

public class Scope {
    private Scope parent;
    private HashMap<String, SymEntry> variables;

    // Used for global scope
    public Scope() {
        this(null);
    }

    // Used for any scope
    public Scope(Scope parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Scope(Scope parent, boolean isFunction) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Scope(Scope parent, HashMap<String, SymEntry> variables) {
        this.parent = parent;
        this.variables = variables;
    }

    public boolean addVariable(String key, SymEntry var) {
        return variables.putIfAbsent(key, var) == null;
    }

    public SymEntry lookup(String key) {
        SymEntry entry = this.variables.getOrDefault(key, null);
        if (entry == null && this.parent != null) {
            entry = this.parent.lookup(key);
        }

        return entry;
    }

    public void reAssign(String id, SymEntry value) {
        variables.replace(id, value);
    }

    public Scope getParent() {
        return this.parent;
    }
}
