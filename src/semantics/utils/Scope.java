package utils;

import java.util.HashMap;

public class Scope {
    private Scope parent;
    private HashMap<String, MSValue> variables;

    // Used for global scope
    public Scope() {
        this(null);
    }

    // Used for any scope
    public Scope(Scope parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    // Not good
    public void addVariable(String key, MSValue var) {
        variables.putIfAbsent(key, var);
    }

    public MSValue lookup(String key) {
        return this.variables.getOrDefault(key, null);
    }

    public void reAssign(String id, MSValue value) {
        variables.replace(id, value);
    }
}
