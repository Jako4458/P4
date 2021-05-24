Program -> NumExpr

NumExpr -> Term NumExpr'

NumExpr' -> '+' NumExpr
         |  '-' NumExpr
         |  λ

Term    -> Expo Term'

Term'   -> '*' Term
        |  '/' Term
        |  '%' Term
        |  λ

Expo    -> Factor Expo'


Expo'   -> 'Pow' Expo        
        | λ

Factor  -> '(' NumExpr ')'          
        |  'id'                   
        |  Number_literal



Program -> NumExpr

NumExpr -> Term ('+' Term | '-' Term)*

Term    -> Expo ('*' Expo | '/' Expo | '%' Expo)*

Expo    -> Factor ('Pow' Factor)*

Factor  -> '(' NumExpr ')'          
        |  'id'                   
        |  Number_literal


2 + 3 ^ 4 * 5
the sum of 2 and the product of the pow of 3 and 4 and 5

2 + 3 ^4 ^ 5
the sum of 2 and the pow of 3, 4, and 5


2 / 3 * 5 / 3 % 5

(((2 / 3) * 5) / 3) % 5

the remainder of the division of the product of the division of 2 and 3 and 5 and 3 and 5

2 * 3 * 4 / 5
the division of the product of 2, 3 and 4 and 5
(the division of (the product of 2,3,4),5)

2 * 3 * 4 * 5
2, 3, 4, and 5

2 / 3 * 5 / 3 % 5

(the remainder of (the division of (the product of (the division of 2, 3), 5), 3), 5) 

2 divided by 3 times 5 divided by 3

2 divided by 3) times 5) divided by 3) mod 5)
t = "(" * numOfRParen + s

s = (s