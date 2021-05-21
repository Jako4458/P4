## Setup ##
This file documents the `setup.json` file and its options.
## Template
Use the template below for easy setup. Make sure to change the output directory.
```json
{
  "outputPath"    : "outputDirectory",
  "debug"         : false,
  "fileMode"      : "cleanup",
  "errorMode"     : "all",
  "commenting"    : true,
  "nameMode"      : "readable",
  "variableMode"  : "delete",
  "containerMode" : "none",
  "containerName" : "result",
  "pedantic"      : "false"
}
```

## Options


| **Option**      | **Values**         | **Default**                    | **Description**      |
|:----------------|:-------------------|:----------------------------|:---------------------|
| `outputPath`    | output directory   | current working directory | The path for the generated files. |
| `debug`         | `true`, `false`    | `false` | Setting `debug` to `true` disables garbage collection. Setting `debug` to `false` enables garbage collection. |
| `fileMode`      | `cleanup`, `keep`    | `cleanup` | Setting `fileMode` to `cleanup` will remove old, duplicate files. Setting `fileMode` to `keep` will in case of duplicate files not overwrite the current file. |
| `errorMode`     | `all`, `errorsOnly`, `none`    | `all` | Setting `errorMode` to `all` will make all errors and warnings visible. Setting `errorMode` to `errorsOnly` will make only errors show. Setting `errorMode` to `none` will make no errors or warnings show. |
| `commenting`    | `true`, `false`    | `false` | Toggles the comments for the generated code.  |
| `nameMode`      | `readable`, `random`    | `random` | Sets the variable names to either `readable` or `random`. `readable` directly uses the variable name for the generated code. `random` generates a UUID of 14 characters for the code generated variable. |
| `variableMode`  | `keep`, `delete`    | `delete` | Either keeps or deletes the variables in Minecraft.  |
| `containerMode` | `named`, `auto`, `none`| `auto` | Specifies whether the output should be contained in a named or auto-generated folder, or whether the output should not be contained in any folder.  |
| `containerName` | directory name | `result` | The name of the container for the output. Will only have an effect if `containerMode` is set to `auto`  |
| `pedantic` | `true`, `false` | `false` | Toggles `pedantic`. If `pedantic` is set to `true`, the compilation will yield unsuccessful should any errors or warnings occur. If `pedantic` is set to `false`, only errors will make the compilation unsuccessful  |
