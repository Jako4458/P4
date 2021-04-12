public class Vector2Value extends MSValue<Vector2> {
    public Vector2Value(Vector2 value, Type type) {
        super(value, type);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
