import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private String path;
    private final ArrayList<MSFile> builtinFunctions = FileManager.initFunctions();

    public FileManager(String path) {
        this.path = path;
    }

    private void buildBuiltin() {
        File folder = new File(path + "/builtin");
        File folder2 = new File(path + "/builtin/functions");
        boolean made;
        try {
            made = folder.mkdir();
            made = folder2.mkdir();
        } catch (SecurityException e) {
            return;
        }

        if (made || folder.exists()) {
            this.path = path + "/builtin/functions";
            int functionsAdded = buildBuiltinFunctions();
            System.out.println("Missed functions: " + functionsAdded);
        }


    }

    private int buildBuiltinFunctions() {
        int missed = 0;
        for (MSFile func : builtinFunctions) {
            try {
                if (func instanceof FFile) {
                    FileWriter fw = new FileWriter(this.path + "/" + func.getName(), false);
                    fw.write(func.write(""));
                    fw.close();
                } else {
                    func.write(this.path + "/");
                }
            } catch (IOException e) {
                missed++;
            }
        }
        return missed;
    }


    public void build() {
        buildContainer();
        buildBuiltin();
    }

    private void buildContainer() {
        File folder = new File(path + "/result");
        boolean made;
        try {
            made = folder.mkdir();
        } catch (SecurityException e) {
            return;
        }

        if (made || folder.exists())
            this.path = path + "/result";
    }

    private static ArrayList<MSFile> initFunctions() {
        return new ArrayList<>() {
            {
                add(BuiltinFuncs.createSetB("active", "varA", "varB", "boolC"));
            }};
    }
}

interface MSFile {
    String getName();
    String write(String folderPath);
}

class FFile implements MSFile {
    public String name;
    public String content;

    public FFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String write(String folderPath) {
        return this.content;
    }
}

class CFile implements MSFile {
    public String name;
    public List<MSFile> content;

    public CFile(String name, List<MSFile> content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String write(String folderPath) {
        FileWriter fw;
        try {
            fw = new FileWriter(folderPath + "/" + this.name + ".mcfunction", false);
            for (MSFile f : this.content) {
                if (f instanceof FFile) {
                    fw.append(f.write(folderPath));
                } else {
                    f.write(folderPath);
                }
            }
            fw.close();
        } catch (IOException e) {
            return null;
        }

        return this.name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
