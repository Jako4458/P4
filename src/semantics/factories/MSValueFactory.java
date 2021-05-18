public class MSValueFactory {

    public int getDefaultNum() {
        return 0;
    }

    public boolean getDefaultBool() {
        return false;
    }

    public BlockValue getDefaultBlock() {
        return new BlockValue("#air", Type._block);
    }

    public String getDefaultString() {
        return "";
    }

    public Vector3Value getDefaultVector3() {
        return new Vector3Value(new Vector3(0,0, 0), Type._vector3);
    }

    public BlockValue createBlockValue(String value) {
        return new BlockValue(value, Type._block);
    }
}
