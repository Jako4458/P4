# Features

## Functions
    Recursive functions
    Void functions
    return functions
    Compilede functions
    NCompilede functions
    (Anonymous functions)

## Variables
    number
    blocks
    vectors (2D, 3D)
    arrays
    boolean

## Data types
    number -> int
    string
    blocks
    vectors
    file
    byte
    boolean

## Conditional control structures
    if
    if else
    if - else if - else
    switch 
    
## Iterative control structures
    for
    for each
    do while
    while
    
## Other control structures
    break
    continue
    return

## File management
    read

## Logical operators
    and, or, xor
    >=, >, <=, <, ==, !=

## Arithmetic operators
    +, -, *, /, POW, SQRT, %
    -=, +=, *=, /=, %=

## Escape character
    $ for MCFunction
    \ for string escape
    
## Other
    Method overloading
    Standard library (no escape character)


# Syntax 

## Variables
    <scope> <id>: <type>               
        where <scope> ∈ {var}, <type> ∈ {num, vec2, vec3, bool, block}
    
    <scope> <id>: <type> = <val>                  
        where <scope> ∈ {var, const}, <type> ∈ {num, vec2, vec3, bool, block}
    
    <scope> <id>: <type> = <val>, <id>: <type> = <val>     
        where <scope> ∈ {var, const}, <type> ∈ {num, vec2, vec3, bool, block}

    <id> = <assigned>
        where <id> is already initialised, <assigned> is either <val>, <id>, or <expr>

## Data types
    num     --> n
        where n ∈ Z
    string  --> n
        where n = "⌢t⌢" and t ∈ Σ* and Σ = all ASCII - {"}
    blocks  --> BLOCK
    vectors --> <<num>, <num>> or <<num>, <num>, <num>>
    file    --> @<string>
    byte    --> <num>
    boolean --> <val>
        <val> ∈ {true, false}
