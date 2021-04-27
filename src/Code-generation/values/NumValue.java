public class NumValue extends MSValue<Integer>{
    public NumValue(Integer value, Type type) {
        super(value, type);
    }

    private NumValue() {

    }

    static public NumValue dupe() {
        return new NumValue();
    }
}
