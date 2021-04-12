import java.util.ArrayList;
import java.util.List;

public class Vector3 implements Vector {
    private final Integer x;
    private final Integer y;
    private final Integer z;

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public List<Integer> getElements() {
        return new ArrayList<>() {{
            add(x);
            add(y);
            add(z);
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
        return this.z;
    }

    @Override
    public String toString() {
        return "<" + this.x + "," + this.y + "," + this.z + ">";
    }
}

