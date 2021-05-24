import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileManager {
    private String path;
    private final ArrayList<MSFile> builtinFunctions = FileManager.initFunctions();

    /**
     * Constructor for the file manager.
     * @param path the initial path to output to
     */
    public FileManager(String path) {
        this.path = path;
    }

    /**
     * Builds the entire builtin functions folder.
     */
    private void buildBuiltin() {
        File folder = new File(path + "/builtin");
        File folder2 = new File(path + "/builtin/functions");
        boolean made;
        try {
            made = folder.mkdir();
            made = made && folder2.mkdir();
        } catch (SecurityException e) {
            return;
        }

        if (made || folder.exists()) {
            String tempPath = new String(path);
            this.path = path + "/builtin/functions";
            buildBuiltinFunctions();
            this.path = tempPath;
        }
    }

    /**
     * Builds all builtin functions using their MSFILEs.
     * @return number of missed functions
     */
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

    /**
     * Builds all necessary folders before code generation.
     */
    public void buildBeforeCodeGen() {
        if (Main.setup.containerMode == ContainerMode.auto)
            buildContainer();
        else if (Main.setup.containerMode == ContainerMode.named) {
            buildContainer(Main.setup.containerName != null ? Main.setup.containerName : "result");
        }
        buildBuiltin();
    }

    /**
     * Builds the default container if the mode is "auto"
     * @see ContainerMode
     */
    private void buildContainer() {
        buildContainer("result");
    }

    /**
     * Builds the container folder.
     * @param containerName The name of the container
     */
    private void buildContainer(String containerName) {
        File folder = new File(path + "/" + containerName);
        boolean made;
        try {
            made = folder.mkdir();
        } catch (SecurityException e) {
            return;
        }

        if (made || folder.exists())
            this.path = path + "/" + containerName;
    }

    /**
     * Uses the templates from code generation to generate all necessary files.
     * If a newFile template is seen, a new file is created.
     * If a endFile template is seen, the current file is exited.
     * Otherwise the contents of the template is simply inserted into the file currently on the top of the stack.
     * @param templates The templates for which files should be generated
     * @return true if successfully built the output, false if unsuccessful
     */
    public boolean buildCodeGen(List<Template> templates) {
        buildFolders();
        Stack<FileWriter> writers = new Stack<>();
        boolean currentFileExists = false;

        for (Template template : templates) {
            try {
                FileWriter currentWriter = writers.size() == 0 ? null : writers.peek();
                if (template instanceof EnterNewFileST) {
                    EnterNewFileST currentTemplate = (EnterNewFileST) template;
                    String pathExtension = currentTemplate.isMcfunction ? "/mcfuncs/functions/" : "/bin/functions/";
                    String fileName = this.path + pathExtension + currentTemplate.fileName + ".mcfunction";
                    if ((new File(fileName)).exists() && Main.setup.fileMode.equals(FileMode.keep)) {
                        currentFileExists = true;
                        continue;
                    }
                    writers.push(new FileWriter(fileName, !currentTemplate.isMcfunction));
                } else if (template instanceof ExitFileST && currentWriter != null) {
                    currentFileExists = false;
                    currentWriter.close();
                    writers.pop();
                } else {
                    if (!currentFileExists && currentWriter != null)
                        currentWriter.write(template.getOutput());
                }
            } catch (IOException | NullPointerException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Builds the folders for the output except the builtin folder.
     */
    private void buildFolders() {
        ArrayList<File> folders = new ArrayList<>() {{
            add(new File(path + "/bin"));
            add(new File(path + "/bin/functions"));
            add(new File(path + "/mcfuncs"));
            add(new File(path + "/mcfuncs/functions"));
        }};

        try {
            for (File folder : folders) {
                if (folder.exists() && Main.setup.fileMode.equals(FileMode.cleanup)) {
                    if (!folder.delete()) {
                        //throw new RuntimeException("Could not create folder " + folder.getName());
                        return;
                    }
                } else if (!folder.mkdir())
                    throw new RuntimeException("Could not create folder " + folder.getName());
            }
        } catch (SecurityException ignored) {
        }
    }

    /**
     * Initialises the functions list.
     * @return the list of functions
     */
    private static ArrayList<MSFile> initFunctions() {
        return new ArrayList<>() {
            {
                add(BuiltinFuncs.createSetB("active", "varA", "varB", "boolC"));
                add(BuiltinFuncs.createTP("active", "varA", "boolC"));
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

    /**
     * Constructor for FFILE (Final FILE, meaning it cannot contain other files).
     * @param name The name of the file
     * @param content The content of the file, which is the string to insert into the file
     */
    public FFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    /**
     * Returns the name of the file
     * @return the name of the file
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the content of this file, which is simply a string.
     * @param folderPath The path for the output. Not used, but most be here, by the interface
     * @return the content of this file
     */
    @Override
    public String write(String folderPath) {
        return this.content;
    }
}

class CFile implements MSFile {
    public String name;
    public List<MSFile> content;

    /**
     * The constructor of CFILE (short for Composite FILE).
     * @param name The name of the file
     * @param content The content of the file given as a list of files
     */
    public CFile(String name, List<MSFile> content) {
        this.name = name;
        this.content = content;
    }

    /**
     * Creates the file and fills the content.
     * Calls itself on all files in the content of the file, until all of the content has been created and filled.
     * @param folderPath The path for the output
     * @return the name of the file
     */
    @Override
    public String write(String folderPath) {
        FileWriter fw;
        if ((new File(folderPath + "/" + this.name + ".mcfunction")).exists() && Main.setup.fileMode.equals(FileMode.keep))
            return this.name;
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

    /**
     * Returns the name of the file.
     * @return the name of the file
     */
    @Override
    public String getName() {
        return this.name;
    }
}
