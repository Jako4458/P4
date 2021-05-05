import javax.xml.xpath.XPathException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileManager {
    private final String originalPath;
    private String path;
    private final ArrayList<MSFile> builtinFunctions = FileManager.initFunctions();

    public FileManager(String path) {
        this.path = path;
        this.originalPath = new String(path);
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
            String tempPath = new String(path);
            this.path = path + "/builtin/functions";
            int functionsAdded = buildBuiltinFunctions();
            System.out.println("Missed functions: " + functionsAdded);
            this.path = tempPath;
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


    public void buildBeforeCodeGen() {
//        buildContainer();
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

    public boolean buildCodeGen(List<Template> templates) {
        buildFolders();
        Stack<FileWriter> writers = new Stack<>();

        for (Template template : templates) {
            try {
                FileWriter currentWriter = writers.size() == 0 ? null : writers.peek();
                if (template instanceof EnterNewFileST) {
                    EnterNewFileST currentTemplate = (EnterNewFileST) template;
                    String pathExtension = currentTemplate.isMcfunction ? "/mcfuncs/functions/" : "/bin/functions/";
                    writers.push(new FileWriter(this.path + pathExtension + currentTemplate.fileName + ".mcfunction", true));
                } else if (template instanceof ExitFileST) {
                    currentWriter.close();
                    writers.pop();
                } else {
                    currentWriter.write(template.getOutput());
                }
            } catch (IOException e) {
                return false;
            } catch (NullPointerException e) {
                return false;
            }
        }

        return true;
    }

    private void buildFolders() {
        File folder1 = new File(path + "/bin");
        File folder2 = new File(path + "/bin/functions");
        File folder3 = new File(path + "/mcfuncs");
        File folder4 = new File(path + "/mcfuncs/functions");
        try {
            folder1.mkdir();
            folder2.mkdir();
            folder3.mkdir();
            folder4.mkdir();
        } catch (SecurityException ignored) {
        }
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
