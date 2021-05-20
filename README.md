# P4 - Minespeak Compiler

Project made by group d402, AAU Computer Science - 4th semester.
Authors: Nikolai A. Bonderup, Jakob B. Hyldgaard, Christian B. Larsen, Sebastian Lassen & Steven Tran
Hand in date: 27/05-2021

Minespeak is a domain specific programming language for Minecraft. This project is a compiler for Minespeak. Minespeak is used to create MCFunctions that can be ran from inside a Minecraft client.

## Requirements(OLD)
To compile and run the compiler, you are required to download ANTLR4 and its associated libraries. Place the ANTLR4 libraries in the lib folder at the root of /the project. Then you are required run ANTLR4 on the file cfg.g4, which will generate the Minespeak parser and lexer.

## Repository Structure
Structure
## Installation

### Running from binaries

### Running from source
The following instructions are based on how the project has been built by the authors. All mentioned directories are located at the project root.

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

To give the compiler an input file, inside IDEA, select `Run -> Edit Configurations`. In program arguments (Hold ALT for field hints), write the location of a file. Example: `./examples/Pyramid.ms`.

#### Compiler setup (optinal)
test test
