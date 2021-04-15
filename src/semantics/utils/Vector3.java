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

    static public Vector3 add(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
    }

    static public Vector3 sub(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY(), vec1.getZ() - vec2.getZ());
    }

    static public Vector3 neg(Vector3 vec) {
        return new Vector3(-vec.getX(), -vec.getY(), -vec.getZ());
    }

    @Override
    public String toString() {
        return "<" + this.x + "," + this.y + "," + this.z + ">";
    }

    public String toString(String PosPrefix) {
        return PosPrefix + this.getX() + PosPrefix + this.getY() + PosPrefix + this.getZ();
    }
}

