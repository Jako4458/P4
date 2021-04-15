import java.util.ArrayList;
import java.util.List;

public class Vector2 implements Vector {
    private final Integer x;
    private final Integer y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public List<Integer> getElements() {
        return new ArrayList<>() {{
            add(x);
            add(y);
        }};
    }

    @Override
    public Integer getX() {
        return this.x;
    }

    @Override
    public Integer getY() {
        return this.y;
    }

    @Override
    public Integer getZ() {
        return null;
    }

    static public Vector2 add(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
    }

    static public Vector2 sub(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY());
    }

    static public Vector2 neg(Vector2 vec) {
        return new Vector2(-vec.getX(), -vec.getY());
    }

    @Override
    public String toString() {
        return "<" + this.x + "," + this.y + ">";
    }

    public String toString(String PosPrefix) {
        return PosPrefix + this.getX() + " " + PosPrefix + this.getY() + " ";
    }
}
