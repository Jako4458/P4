import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class ArrayType extends Type{
    public Type type;
    public ArrayType(ParseTree tree, Type type) {
        super(type.tree);
        this.type = type;
    }

    @Override
    public String toString() {
        return "array["+type+"]";
    }

    public boolean equalTypes(ArrayType other) {
        return other.type == this.type;
    }
}
