public class ArrayEntry implements SymEntry {
    private String name;
    private ArrayType type;
    private Type baseType;
    private int size;

    public ArrayEntry(String name, ArrayType type, int size) {
        this.name = name;
        this.type = type;
        this.baseType = this.type.type;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public int getSize() {
        return this.size;
    }

    public Type getBaseType() {
        return this.baseType;
    }
}
