//import Logging.Logger;
//import Logging.VariableAlreadyDeclaredError;

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
    public boolean addVariable(String key, SymEntry var) {
        return variables.putIfAbsent(key, var) == null;
    }

    public SymEntry lookup(String key) {
        SymEntry entry = this.variables.getOrDefault(key, null);
        if (entry == null && this.parent != null && !this.parent.isFunction) {
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

    public boolean isFunction() {
        return this.isFunction;
    }

    public void MarkNextFuncAsMCFunc() {
        this.nextFuncIsMCFunc = true;
    }
}
