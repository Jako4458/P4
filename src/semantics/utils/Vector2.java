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

    @Override
    public String toString() {
        return "<" + this.x + "," + this.y + ">";
    }
}
