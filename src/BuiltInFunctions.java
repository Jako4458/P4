import java.util.ArrayList;

public class BuiltInFunctions {

    private ArrayList<String> functions;
    private String teleportFunc =
            System.lineSeparator() +
                    "func teleport(location:vector3,relative:bool) do" + System.lineSeparator() +
                    "if not relative do" + System.lineSeparator() +
                    "$teleport ^v{location}" + System.lineSeparator() +
                    "else do" + System.lineSeparator() +
                    "$teleport v{location}" + System.lineSeparator() +
                    "endif" + System.lineSeparator() +
                    "endfunc" + System.lineSeparator();

    public BuiltInFunctions() {
        functions = new ArrayList<>();
        functions.add(teleportFunc);
    }

    public ArrayList<String> getFunctions() {
        return functions;
    }
}
