public class Helper {

    public static SymEntry getEntryFromParent(Node parent, String key) {
        SymEntry lookup = parent.getTable().getEntry(key);
        if (lookup == null && parent.getParent() != null) {
            lookup = getEntryFromParent(parent.getParent(), key);
        }
        return lookup;
    }




    public static boolean isPrimitiveBooleanType(String v) {
        return v.equals("boolean");
    }


}
