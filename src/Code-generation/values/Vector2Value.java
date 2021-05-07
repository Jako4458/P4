public class Vector2Value extends MSValue<Vector2> {
    public Vector2Value(Vector2 value, Type type) {
        super(value, type);
    }

    private Vector2Value() {

    }

    public static Vector2Value dupe() {
        return new Vector2Value();
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
