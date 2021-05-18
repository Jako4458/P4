import java.util.Objects;

public abstract class Value {
    protected Type type;

    public Value(Type type) {
        this.type = type;
    }

    public abstract Object getValue();

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value)) return false;
        return Objects.equals(getValue(), ((Value) o).getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
