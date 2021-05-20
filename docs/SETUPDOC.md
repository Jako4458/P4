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
| `outputPath`    | output directory   | current working directory | Description |
| `debug`         | `true`, `false`    | `false` | Description |
| `fileMode`      | `cleanup`, `keep`    | `cleanup` | Description |
| `errorMode`     | `all`, `errorsOnly`, `none`    | `all` | Description  |
| `commenting`    | `true`, `false`    | `false` | Description  |
| `nameMode`      | `readable`, `random`    | `random` | Description  |
| `variableMode`  | `keep`, `delete`    | `delete` | Description  |
| `containerMode` | `named`, `auto`, `none`| `auto` | Description  |
| `containerName` | directory name | `result` | Description  |
| `pedantic` | `true`, `false` | `false` | Description  |
