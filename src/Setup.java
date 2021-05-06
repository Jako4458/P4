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

enum FileMode {
    cleanup, keep
}

enum ErrorMode {
    all, errorsOnly, none
}

enum NamingMode {
    readable, random
}

enum VariableMode {
    keep, delete
}

enum ContainerMode {
    named, auto, none
}

