public class STemplateFactory {

    public InstanST createInstanST(SymEntry entry) {
        if (entry.getType() == Type._vector2)
            return new InstanST(getName(entry), entry.getValue().getCasted(Vector2Value.class));
        if (entry.getType() == Type._vector3)
            return new InstanST(getName(entry), entry.getValue().getCasted(Vector3Value.class));

        return new InstanST(getName(entry), getValAsString(entry));
    }

    private String getValAsString(SymEntry entry) {

        if (entry.getType() == Type._num)
            return Value.value(entry.getValue().getCasted(NumValue.class)).toString();
        if (entry.getType() == Type._bool)
            return Value.value(entry.getValue().getCasted(BoolValue.class)) ? "1" : "0" ;
        if (entry.getType() == Type._vector2)
            return Value.value(entry.getValue().getCasted(Vector2Value.class)).toString("~");
        if (entry.getType() == Type._vector3)
            return Value.value(entry.getValue().getCasted(Vector3Value.class)).toString("~");

        return entry.toString();
    }

    private String getName(SymEntry entry) {return entry.getName() + "_" + entry.toString();}

}
