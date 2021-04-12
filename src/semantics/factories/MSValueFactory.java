public class MSValueFactory {

    public Value createMSValue(Object value, Type type) {
        switch (type.getTypeAsInt()) {
            case Type.NUM: return new NumValue((Integer)value, type);
            case Type.BOOL: return new BoolValue((Boolean)value, type);
            case Type.BLOCK: return new BlockValue((String)value, type);
            case Type.STRING: return new StringValue((String)value, type);
            case Type.VECTOR2: return new Vector2Value((Vector2)value, type);
            case Type.VECTOR3: return new Vector3Value((Vector3)value, type);
            default: return null;
        }
    }

    public Value createValue(Integer value, Type type) {
        return new NumValue(value, type);
    }

    public Value createValue(Boolean value, Type type) {
        return new BoolValue(value, type);
    }

    public Value createValue(String value, Type type) {
        if (type.getTypeAsInt() == Type.BLOCK)
            return new BlockValue(value, type);
        return new StringValue(value, type);
    }

    public Value createValue(Vector2 value, Type type) {
        return new Vector2Value(value, type);
    }

    public Value createValue(Vector3 value, Type type) {
        return new Vector3Value(value, type);
    }

}
