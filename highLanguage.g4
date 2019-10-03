
grammar highLanguage;

/*
 * Lexer Rules
 */
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
DIV_MATH_OP: '/' ;
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

program: (atomicBlock | block)* ;

// blocks must have at least one statement
atomicBlock: LT_OP (stmt)+ GT_OP ;
block: (stmt)+ ;

stmt:  whileStmt | ifStmt | assignStmt | awaitStmt;
condExp: compExp | condDualExp;
condDualExp: compExp (AND_OP | OR_OP) compExp;


whileStmt:   WHILE LPAR condExp RPAR LBRA stmt* RBRA ;
ifStmt:      IF LPAR condExp RPAR LBRA stmt* RBRA (ELSE LBRA stmt* RBRA)? ;
assignStmt:  IDENTIFIER ASSIGN valueExp SC ;
awaitStmt:   AWAIT condExp GT_OP SC ;

compExp: singleValue (EQ_OP | NE_OP | LT_OP | GT_OP) singleValue ;


valueExp: singleValue | additionExp | subExp | multExp | divExp ;
additionExp: singleValue ADD_MATH_OP singleValue ;
subExp: singleValue SUB_MATH_OP singleValue ;
multExp: singleValue MULT_MATH_OP singleValue ;
divExp: singleValue DIV_MATH_OP singleValue ;
singleValue: INT_LITERAL | IDENTIFIER;


// mathematical operations
//additionExp: multiplyExp ( ADD_MATH_OP multiplyExp | SUB_MATH_OP multiplyExp)* ;
//multiplyExp: atomExp ( MULT_MATH_OP atomExp | DIV_MATH_OP atomExp)* ;
//atomExp : INT_LITERAL | IDENTIFIER | LPAR additionExp RPAR ;

// conditional expressions
//andExp: orExp (OR_OP orExp)* ;
//orExp: compExp (AND_OP compExp)* ;
//compExp: valueExp (EQ_OP | NE_OP | LT_OP | GT_OP) valueExp | LPAR andExp RPAR ;