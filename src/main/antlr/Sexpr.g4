grammar Sexpr;

sexpr
    : item* EOF
    ;

item
    : atom
    | list_
    | LPAREN item DOT item RPAREN
    ;

list_
    : LPAREN item* RPAREN
    ;

atom
    : STRING
    | SYMBOL
    | NUMBER
    | DOT
    ;

STRING
    : '"' ('\\' . | ~ ('\\' | '"'))* '"'
    ;

WHITESPACE
    : (' ' | '\n' | '\t' | '\r')+ -> skip
    ;

NUMBER
    : ('+' | '-')? (DIGIT)+ ('.' (DIGIT)+)?
    ;

SYMBOL
    : SYMBOL_START (SYMBOL_START | DIGIT)*
    ;

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

DOT
    : '.'
    ;

fragment SYMBOL_START
    : ('a' .. 'z')
    | ('A' .. 'Z')
    | '+'
    | '-'
    | '*'
    | '/'
    | '.'
    ;

fragment DIGIT
    : ('0' .. '9')
    ;