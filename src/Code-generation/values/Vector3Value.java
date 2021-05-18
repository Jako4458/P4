public class Vector3Value extends MSValue<Vector3> {
    public Vector3Value(Vector3 value, Type type) {
        super(value, type);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
