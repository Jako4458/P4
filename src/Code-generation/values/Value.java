import java.lang.reflect.ParameterizedType;

public abstract class Value {
    protected Type type;

    public Value(Type type) {
        this.type = type;
    }

    public <T extends Value> T getCasted(Class<T> classType) {
        switch (type.getTypeAsInt()) {
            case Type.NUM: return classType.cast((NumValue.dupe()).getClass().cast(this));
            case Type.BOOL: return classType.cast((BoolValue.dupe()).getClass().cast(this));
            case Type.STRING: return classType.cast((StringValue.dupe()).getClass().cast(this));
            case Type.BLOCK: return classType.cast((BlockValue.dupe()).getClass().cast(this));
            case Type.VECTOR2: return classType.cast((Vector2Value.dupe()).getClass().cast(this));
            case Type.VECTOR3: return classType.cast((Vector3Value.dupe()).getClass().cast(this));
            default: return null;
        }
    }

    public abstract Object getValue();

    static public Integer value(NumValue value) {
        return value.getValue();
    }
    static public Boolean value(BoolValue value) {
        return value.getValue();
    }
    static public String value(BlockValue value) {
        return value.getValue();
    }
    static public String value(StringValue value) {
        return value.getValue();
    }
    static public Vector2 value(Vector2Value value) {
        return value.getValue();
    }
    static public Vector3 value(Vector3Value value) {
        return value.getValue();
    }
}
