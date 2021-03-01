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
    foreach
    do while
    while
    
For iterative control structures the language will provide four methods; namely for, foreach, do while, and while.
For will use an iterator to iterator for certain values.
Foreach will iterate over all elements of an array.
Do while will perform certain actions once, and then continue to do so depending on a condition.
While will perform certain actions while a certain condition is held.

## Flow control
    break
    continue
    return

Flow control are keywords for controlling the flow of the code.
Break will stop the for/while loop or stop a case fallthrough in a switch.
Continue will skip the current iteration of a for/while loop.
Return will stop a function and return the appropriate value to the place it was called.

## File management
    read

Read will read a byte array from a file containing the information.

## Logical operators
    and, or, xor, not
    >=, >, <=, <, ==, !=
    a <> (b, c)

These operators hold the usual logical/mathematical meaning. 
The final "a <> (b, c)" will return true if a is in the range defined by b and c.

## Arithmetic operators
    +, -, *, /, POW, SQRT, %

These operators can be used for mathematical expressions.
They will in the case of a fraction return the nearest even number by convention.

## Compound assignment
    -=, +=, *=, /=, %=

Compound assignment can be used to alter a variable without having to use an assignment with an arithmetic operator involving the variable itself. 

## Escape character
    $ for MCFunction
    \ for string escape
    
Escape characters will be used escaping a certain context, and using functionality not usually accessible in the context.
One will be for MCFunctions, as the language will allow the user to define an MCFunction as defined for that language.
Another will be to escape from strings to use another functionality than the string literal of the character. 

## Other
    Method overloading
    Standard library (no escape character)

The language will support method overloading, where two functions can share name given that they do not share return type or parameters. 
The language will support a standard built-in library with functionality often used for building.

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

Non-compiled functions (not having @mc) can either return a value of <ret_type> or not return anything.
@mc functions will be compiled directly to MCFunction functions. These cannot have a return type, or a return statement.
Anon functions are non-compiled functions without a name. 
Other than that the syntax remains (except for using anon instead of func).
 
Examples:

    func Add(num a, num b) -> num do
        return a + b
    endfunc

    func Print(num a, num b) do
        Say(a + b)      % Say will be part of the standard built-in library, which will print something to the chat in Minecraft
    endfunc

    @mc
    func BuiltCastle(num numOfTowers, block material) do
        ...             % Statements for bulding a castle
    endfunc

    anon (num a, num b) -> boolean do
        return a == b
    endanon

    anon (block mat) do
        Say(mat)         % States the block type of material    
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

Variables can be declared and initialised in the same statement. 
Const variables cannot be declared and not initiliased. 
Multi-variable declarations are seperated by commas.

Examples:

    var a: num, b: num
    var c: boolean = true
    const TEM: num = 100
    

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

    switch <val> do
        case <val>:
            <statement>
            ...
            break;
        ...
        default:
            <statement>
            ...
    endswitch

If-statements have three types; if, elseif, else.
If can be followed by elseif or else
Elseif can be followed by elseif or else
Switch evaluates over a number value to match to cases. Fallthrough will happen if break is not directly used

Examples:

    if true do
        Say(5)
    elseif false do
        Say(4)
    else do
        Say(3)

    switch 5
    case 3: 
        Say(3)
        break
    case 5:
        Say(5)
        break
    default:
        Say(0)

## Iterative control structures
    for <temp_id> <assign> <num> until <temp_id> <log_op> <num> where <id> <comp_assign> <num> do
        <statement>
        ...
    endfor

    foreach <temp_id> in <id> do
        <statement>
        ...
    endfor

    do
        <statement>
        ...
    while <expression> endwhile

    while <expression> do
        <statement>
        ...
    endwhile

For loops uses an implicitly declared iterator which can be given an id not currently in use.
The for loops uses *until* to describe under which boolean expression related to the iterator should the loop terminate.
Finally it also describes which change to the value should be applied to the iterator after each iteration.
Foreach implicitly declares a temporary variable which can be given an id not currently in use. 
The foreach loop will go through all elements of an array, in the order they appear within the array.
Do while will perform a number of statements once, and a while expression (of type boolean) evaluates whether the loop should persist.
While is the same as do while, but the while expression is evaulated before any statements are performed. 

Examples:

    for i = 0 until i == 5 where i += 1 do
        Say(i)
    endfor

    var array: int[] = [0, 1, 2]
    foreach ele in array do
        Say(ele)
    endforeach

    do
        Say(1)
    while (true == false) enddow

    while (true == false) do
        Say(1)
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

Logical operator are split into those that use boolean operands and those which uses numerical operands.
The boolean operators are and, or, xor, not. And, or, xor are binary and uses two binary operands. Not is unary and takes only one operand.
The numerical operators are all binary taking two num operands. 
The expression will be of boolean value for both types.
The final one is <num> <> (<num>, <num>) which indicates whether a number is in a certain range. 

Examples:

    var a: boolean = true, b: boolean = false

    a or b          % the value of this expression is true

    var a: num = 3, b: num = 4

    a >= b          % the value of this expression is false

    a <> (2, 5)     % the value of this expression is true

## Arithmetic operators
    <num> <op> <num> -> <num>
        where <op> ∈ {+, -, *, /, POW, %}
    
    <op> <num> -> <num>
        where <op> ∈ {SQRT}
    
There are two types of arithmetic operators: binary and unary. 
Binary operators will use two operands, where unary operators will only use one operand.
The only unary operator is SQRT which takes the square root of an operand.
All operands must be initialised and declared before used for these operators. 

Examples:

    var a: num = 2, b: num = 3, c: num = 4

    a + b       % the two operands a and b are added
    SQRT c      % the expression will attain the value of the square root of the operand c

## Compound assignment
    <id> <comp_assign> <num>
        where <comp_assign> ∈ {-=, +=, *=, /=, %=}

Compund assignments will on the left side have an id of an initliased and declared number variable.
Following this a compound assignment will be used to match the desired effect.
Finally a number expression will be on the right side, for which the arithmetic operator matching the compound assignment will use. 

Example:
    
    var a: num = 5
    a *= 2      % a is now equal to 2 * 5 = 10
