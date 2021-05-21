public class Setup {
    public String outputPath;
    public String inputPath;
    public boolean debug;
    public FileMode fileMode;
    public ErrorMode errorMode;
    public boolean commenting;
    public NamingMode nameMode;
    public VariableMode variableMode;
    public boolean pedantic;
    public ContainerMode containerMode;
    public String containerName;

    /**
     * Constructor to enter all settings manually through parameters.
     */
    public Setup(String outputPath, String inputPath, boolean debug, FileMode fileMode, ErrorMode errorMode, boolean commenting, NamingMode nameMode, VariableMode variableMode, boolean pedantic, ContainerMode containerMode) {
        this.outputPath = outputPath;
        this.inputPath = inputPath;
        this.debug = debug;
        this.fileMode = fileMode;
        this.errorMode = errorMode;
        this.commenting = commenting;
        this.nameMode = nameMode;
        this.variableMode = variableMode;
        this.pedantic = pedantic;
        this.containerMode = containerMode;
    }

    /**
     * The default constructor for the default setup.
     */
    public Setup() {
        this.outputPath = System.getProperty("user.dir");
        this.inputPath = System.getProperty("user.dir");
        this.debug = false;
        this.fileMode = FileMode.cleanup;
        this.errorMode = ErrorMode.all;
        this.commenting = false;
        this.nameMode = NamingMode.random;
        this.variableMode = VariableMode.delete;
        this.pedantic = false;
        this.containerMode = ContainerMode.auto;
    }
}

/**
 * If set to cleanup, old duplicate files will be overwritten.
 * If set to keep, old duplicate files are kept intact, and the new files are not generated.
 */
enum FileMode {
    cleanup, keep
}

/**
 * If set to all, all errors and warnings will be reported.
 * If set to errorsOnly, only errors will be reported.
 * If set to none, no errors or warnings will be reported.
 */
enum ErrorMode {
    all, errorsOnly, none
}

/**
 * If set to readable, the original variable names are kept for the code generation.
 * This can potentially cause name clashes and thus it is unsafe and error prone.
 * If set to random, new UUIDs will be generated during code generation for variable names.
 */
enum NamingMode {
    readable, random
}

/**
 * If set to keep, variables will not be garbage collected.
 * If set to delete, variables will be garbage collected.
 */
enum VariableMode {
    keep, delete
}

/**
 * If set to named, the folder for the output will be specified by a setting called: containerName.
 * If set to auto, the folder for the output will have an auto generated name.
 * If set to none, no folder for the output will be created.
 */
enum ContainerMode {
    named, auto, none
}

