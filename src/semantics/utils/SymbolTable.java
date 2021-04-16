public interface SymbolTable {

    public SymEntry getEntry(String hashCode);
    public SymEntry addEntry(SymEntry entry);
    public SymEntry lookup(SymEntry entry);
    public SymEntry lookup(String hashCode);

}
