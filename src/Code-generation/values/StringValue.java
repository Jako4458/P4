public class StringValue extends MSValue<String> {
    public StringValue(String value, Type type) {
        super(value, type);
    }

    private StringValue() {

    }

    public static StringValue dupe() {
        return new StringValue();
    }
}
