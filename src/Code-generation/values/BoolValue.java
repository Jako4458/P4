public class BoolValue extends MSValue<Boolean> {
    public BoolValue(Boolean value, Type type) {
        super(value, type);
    }

    private BoolValue() {

    }

    public static BoolValue dupe() {
        return new BoolValue();
    }
}
