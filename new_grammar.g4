grammar new_grammar;

/*
 * Lexer Rules
 */
KEYWORD_INT: 'int' ;
ASSIGN: '=' ;
LBRA: '{' ;
RBRA: '}' ;
LPAR: '(' ;
RPAR: ')' ;
SC: ';' ;
IF: 'if' ;
ELSE : 'else' ;
WHILE: 'while' ;
INT_LITERAL: [0-9]+ ;
AND_OP: '&&' ;
OR_OP: '||';
EQ_OP: '==';
NE_OP: '!=' ;
LT_OP: '<';
GT_OP: '>';
MULT_MATH_OP: '*';
DIV_MATH_OP: '\\' ;
ADD_MATH_OP: '+';
SUB_MATH_OP : '-' ;
IDENTIFIER: [a-z]+ ;
AWAIT: '<AWAIT';
WS : [ \t\r\n]+ -> channel(HIDDEN);
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;

/*
 * Parser Rules
 */

program: block ;
block: (varDecl)* (atomicStmt | stmt)* ;

stmt: WHILE LPAR condExp RPAR LBRA stmt? RBRA | IF LPAR condExp RPAR LBRA stmt? RBRA (ELSE LBRA stmt? RBRA)?  | IDENTIFIER '=' additionExp SC | AWAIT condExp GT_OP SC;
atomicStmt: LT_OP (stmt)+ GT_OP ;
condExp: andExp;

// conditional expressions
andExp: orExp (OR_OP orExp)* ;
orExp: compExp (AND_OP compExp)* ;
compExp: additionExp (EQ_OP | NE_OP | LT_OP | GT_OP) additionExp | LPAR andExp RPAR ;

varDecl: KEYWORD_INT IDENTIFIER (SC | '=' (SUB_MATH_OP)? INT_LITERAL SC) ;

// mathematical operations
additionExp: multiplyExp ( ADD_MATH_OP multiplyExp | SUB_MATH_OP multiplyExp)* ;
multiplyExp: atomExp ( MULT_MATH_OP atomExp | DIV_MATH_OP atomExp)* ;
atomExp : INT_LITERAL | IDENTIFIER | LPAR additionExp RPAR ;