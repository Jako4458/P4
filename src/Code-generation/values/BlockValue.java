public class BlockValue extends MSValue<String> {
    public BlockValue(String value, Type type) {
        super(value, type);
    }

    private BlockValue() {

    }

    public static BlockValue dupe() {
        return new BlockValue();
    }
}
