public class MSValue<T> extends Value{
    private T value;

    public MSValue(T value, Type type) {
        super(type);
        this.value = value;
    }

    protected MSValue() {
        super(Type._void);
    }

    @Override
    public T getValue() {
        return this.value;
    }
}
