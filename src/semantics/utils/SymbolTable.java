public interface SymbolTable {

    SymEntry getEntry(String hashCode);
    SymEntry addEntry(SymEntry entry);
    SymEntry lookup(SymEntry entry);
    SymEntry lookup(String hashCode);

}
