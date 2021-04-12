public class Vector3Value extends MSValue<Vector3> {
    public Vector3Value(Vector3 value, Type type) {
        super(value, type);
    }

    private Vector3Value() {

    }

    public static Vector3Value dupe() {
        return new Vector3Value();
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
