import org.antlr.v4.runtime.tree.ParseTree;

public class ArrayType extends Type{
    public Type type;
    public ArrayType(ParseTree tree, Type type) {
        super(tree);
        this.type = type;
    }

    @Override
    public String toString() {
        return "array["+type+"]";
    }
}
