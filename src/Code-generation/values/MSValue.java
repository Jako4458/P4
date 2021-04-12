public class MSValue<T> extends Value{
    private T value;
    private Type type;

    public MSValue(T value, Type type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
