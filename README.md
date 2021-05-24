# P4 - Minespeak Compiler

Project made by group d402, AAU Computer Science - 4th semester.

Authors: Nikolai A. Bonderup, Jakob B. Hyldgaard, Christian B. Larsen, Sebastian Lassen & Steven Tran

Hand in date: 27/05-2021

Minespeak is a domain specific programming language for Minecraft. This project is a compiler for Minespeak. Minespeak is used to create MCFunctions that can be ran from inside a Minecraft client. 

## Repository Structure
Structure
## Installation
All mentioned directories are located at the project root.

### Running from binaries
A compiled version can be found in the [build/](./build) folder. This requires Java Runtime. Tested with version Java SE 16 (version 16.0.1) which can be found [here](https://www.oracle.com/java/technologies/javase-downloads.html). 

Open your shell and do:

```shell
cd ./build/
```

(Optional): Create a file called `setup.json`. See the [`docs/SETUPDOC.md`](./docs/SETUPDOC.md) file for setup documentation.

Create an `example.ms` file and paste the following into the file:

```
minespeak
  @mc
  func example() do
    var four:num = 2 + 2
  endfunc
closespeak
```

Find the correct Java Runtime binary. Add to PATH if necessary.
Compile using:
```shell
path/to/java16/java -jar ./P4.jar ./example.ms
```

If no `outputPath` was specified, output can be found in the `result` folder.

### Running from source
The following instructions are based on how the project has been built by the authors. 

Prerequisites needed before building are as follows:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/). (version 2021.1.1)
- The [ANTLR v4](https://plugins.jetbrains.com/plugin/7358-antlr-v4) IntelliJ IDEA plugin

Download the ANTLR 4 binaries from the ANTLR website [here](https://www.antlr.org/download/antlr-4.9.2-complete.jar). Place this file inside the folder `lib/`. Create the folder if it does not exist and place the file inside it.

Open the project inside IDEA. Before compiling, the ANTLR recognizer has to be generated. Inside IDEA, right click the file called `Minespeak.g4` located inside `parser/` and select the option `Configure ANTLR`. Ensure the following:
- Output directory is `./parser/gen/`
- Language is `Java`
- `generate parse tree listener` is checked.
- `generate parse tree visitor` is checked.

After doing the above, right click `Minespeak.g4` and select `Generate ANTLR Recognizer`. This should succesfully build the grammar and place the generated files inside the folder located at `/parser/gen`.

To give the compiler an input file, inside IDEA, select `Run -> Edit Configurations`. In program arguments (Hold ALT for field hints), write the location of a file. Example: `./Examples/Pyramid.ms`.

### Compiler setup (optional)
To specify compile arguments, a setup file is used. Create a file called `setup.json`. When running from source, place this file at the project root. When running from binaries, place the file in the same directory as the binary. Documentation for the setup file can be found in [`docs/SETUPDOC.md`](./docs/SETUPDOC.md) file.

If no setup file is used, default values are used instead.

Now, run `main` to compile the compiler and compile a Minespeak file.

## Resources

- [Built-in functions](./docs/BUILTINDOC.md)

## Minespeak Examples

Example programs can be found in the [examples](./Examples) folder.
