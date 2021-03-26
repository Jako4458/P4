
import java.util.HashMap;

public class SymTab implements SymbolTable {
    private TreeNode node;
    private HashMap<String, SymEntry> entries = new HashMap<>();

    public SymTab(TreeNode node) {
        this.node = node;
    }

    @Override
    public SymEntry getEntry(String hashCode) {
        return entries.getOrDefault(hashCode, null);
    }

    @Override
    public SymEntry addEntry(SymEntry entry) {
        return entries.putIfAbsent(entry.getName(), entry) != null ? null : entry;
    }

    @Override
    public SymEntry lookup(SymEntry entry) {
        return lookup(entry.getName());
    }

    @Override
    public SymEntry lookup(String key) {
        SymEntry lookup = this.entries.getOrDefault(key, null);
        if (lookup == null) {
            lookup = Helper.getEntryFromParent(node.getParent(), key);
        }
        return lookup;
    }
}
