public class SimpleEntry implements SymEntry {
    private Type type;
    private String name;

    public SimpleEntry(String id, Type type) {
        this.name = id;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }
}
