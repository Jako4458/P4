# Features

## Functions
    Recursive functions
    No return functions
    Return functions
    Compilede functions
    NCompilede functions
    (Anonymous functions)

To support a level of abstraction that will enhance readability and writability, the function concept will be implemented for the language. 
Among the functions there should be a distinction between the return type of the functions, allowing the return of (at least) the primitive types (data types). 
Functions should also be allowed to not return emulating a procedure more so than a function. 
The language should support recursion, as MCFunction utilises this idea, and excluding it may cause unnecessary confusion. 
Functions should also be distinguishable regarding whether they be compiled or not. 
The language should provide a marker for which functions should be compiled and thus be usable inside Minecraft.
The non-compiled functions will act as helper function to enhance the readability of the compiled functions (through abstraction).
Finally, the language may support anonymous functions to encapsulate functions only needed once (typically for function calls).

## Variables
    number
    blocks
    vectors (2D, 3D)
    arrays
    boolean

The language should support variables of the number data type, block data type, vector data types, boolean data type, and finally for arrays.
Arrays will be a collection of elements indexed by numbers with a fixed size. Elements cannot be added or removed from an array. 
Arrays can hold the following types: number, blocks, vectors (2D, 3D), and booleans.

## Data types
    number -> int
    string
    block
    vector
    file
    byte
    boolean

The langauge will support a number of primitive data types, where some will be able to be used as variables and other are only for literals.
Number: number will be the data type encapsulating integer types. As there is no support for floats, the name number is chosen as a more simple and understandable name for the integer type for this language. 
String: strings will be a sequence of characters (not a type). Strings will primarily be used for writing messages or assigning descriptions or names.
Block: block is the type which will indicate the block material. It will be represented as a built-in enum, and it will match the block types from Minecraft
Vector: two types of vectors will be accessible for this language, namely 2-dimensional and 3-dimensional vectors, able to store only number literals. These will typically be used for defining coordinates and directions for the building process of Minecraft. 
File: a file data type will be provided to allow access to files. The file data type will be a specially formatted string for the file path.
Byte: the byte data type may be used when reading files.
Boolean: a representation of true or false.

## Conditional control structures
    if
    if else
    if - else if - else
    switch 
    
Four conditional control structures will be provided from the language. Three of these are accounted for by if-else chains. 
The if statement will evaluate a logical expression and based on the boolean value of this either perform or not a certain act.
Else if can be chained with an if statement to consider another statement if the previous if or if else failed
Else can be chained to if or else if statements to ensure a certain action should happen if all previous if and else if statements failed.
Switch evaluates a value and navigates to the corresponding case. Case fallthrough is supported and can be stopped using break. 

## Iterative control structures
    for
    for each
    do while
    while
    


## Flow control
    break
    continue
    return

Break will stop the for/while loop or stop a case fallthrough in a switch.
Continue will skip the current iteration of a for/while loop.
Return will stop a function and return the appropriate value to the place it was called.

## File management
    read

Read will read a byte array from a file containing the information.

## Logical operators
    and, or, xor, not
    >=, >, <=, <, ==, !=
    a < b < c

These operators hold the usual logical/mathematical meaning. 

## Arithmetic operators
    +, -, *, /, POW, SQRT, %

## Compound assignment
    -=, +=, *=, /=, %=

## Escape character
    $ for MCFunction
    \ for string escape
    
## Other
    Method overloading
    Standard library (no escape character)

## MCFunction
The features presented here are functionality that are usable in MCFunction, but contains a significant amount of overhaed to be used as mcfunction commands. The language will represent these as a type of built in standard library. This will give access to the following features.

    attribute
    clone
    fill
    say
    setblock
    summon
    teleport
    particles

Outside of MCFunction

    Block tracking
    Delete blocks   (fill air)
    Set offset
    Set absolute coordinates
    Hollow with width
    Various shapes
    Check for blocks
    Select blocks
    Math functions?
    Pictures?



# Syntax 

## Functions
    func <id>(<param_type> <param_id>, ...) -> <ret_type> do
        <statement>
        ...
        return <val>
    endfunc

    func <id>(<param_type> <param_id>, ...) do
        <statement>
        ...
    endfunc

    @mc
    func <id>(<param_type> <param_id>, ...) do
        <statement>
        ...
    endfunc

    anon (<param>, ...) -> <ret_type> do 
        <statement>
        ...
        return <val>
    endanon

    anon (<param>, ...) do 
        <statement>
        ...
        return <val>
    endanon

## Variables
    <access> <id>: <type>               
        where <access> ∈ {var}, <type> ∈ Variables
    
    <access> <id>: <type> = <val>                  
        where <access> ∈ {var, const}, <type> ∈ Variables
    
    <access> <id>: <type> = <val>, <id>: <type> = <val>     
        where <access> ∈ {var, const}, <type> ∈ Variables

    <id> = <assigned>
        where <id> is already initialised, <assigned> is either <val>, <id>, or <expr>

## Data types
    num     --> n
        where n ∈ Z
    string  --> n
        where n = "t" and t ∈ Σ* and Σ = all ASCII - {"}
    blocks  --> BLOCK
    vectors --> <<num>, <num>> or <<num>, <num>, <num>>
    file    --> @<string>
    byte    --> <num>
    boolean --> <val>
        <val> ∈ {true, false}

## Conditional control structures
    ||do <statement> if <expression> 

    if <expression> do
        <then>
        ...
    endif

    if <expression> do
        <then>
        ...
    else do
        <else>
        ...
    endif

    if <expression> do
        <then>
        ...
    elseif <expression> do
        <else-then>
        ...
    else do
        <else>
        ...
    endif

## Iterative control structures
    for <temp_id> <assign> <num> until <temp_id> <log_op> <num> where <id> <comp_assign> <num> do
        <statement>
        ...
    endfor

    foreach <temp_id> in <id> do
        <statement>
        ...
    endforeach

    do
        <statement>
        ...
    while <expression> enddow

    while <expression> do
        <statement>
        ...
    endwhile

## Flow control
    break
    continue
    return  <opt_val>

## File management
    read <file>

## Logical operators
    <expression> <log_op> <expression> -> <expression> 
        where <log_op> ∈ {and, or, xor, not}
    
    <num> <num_op> <num> -> <expression>
        where <num_op> ∈ {>=, >, <=, <, ==, !=} 
    
    <num> <> (<num>, <num>) 

## Arithmetic operators
    <num> <op> <num> -> <num>
        where <op> ∈ {+, -, *, /, POW, %}
    
    <op> <num> -> <num>
        where <op> ∈ {SQRT}
    
## Compound assignment
    <id> <comp_assign> <num>
        where <comp_assign> ∈ {-=, +=, *=, /=, %=}




