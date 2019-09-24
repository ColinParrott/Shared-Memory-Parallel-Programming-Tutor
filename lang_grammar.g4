grammar lang_grammar;

/*
 * Lexer Rules
 */
IDENTIFIER: [a-z]+ ;
ASSIGN: '=' ;
LBRA: '{' ;
RBRA: '}' ;
LPAR: '(' ;
RPAR: ')' ;
SC: ';' ;
KEYWORD_INT: 'int' ;
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
ADD_MATH_OP: '+';
SUB_MATH_OP : '-' ;
MULT_MATH_OP: '*';
DIV_MATH_OP: '\\' ;



WS : [ \t\r\n]+ -> channel(HIDDEN);
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;


/*
 * Parser Rules
 */
program: block ;
block: (varDecl)* (stmt)* ;

stmt: WHILE LPAR condExp RPAR LBRA stmt? RBRA | IF LPAR condExp RPAR LBRA stmt? RBRA (ELSE LBRA stmt? RBRA)?  | IDENTIFIER '=' additionExp SC ;

condExp: 'todo' ;

varDecl: KEYWORD_INT IDENTIFIER (SC | '=' (SUB_MATH_OP)? INT_LITERAL SC) ;

// mathematical operations
additionExp: multiplyExp ( ADD_MATH_OP multiplyExp | SUB_MATH_OP multiplyExp)* ;
multiplyExp: atomExp ( MULT_MATH_OP atomExp | DIV_MATH_OP atomExp)* ;
atomExp : INT_LITERAL | IDENTIFIER | LPAR additionExp RPAR ;