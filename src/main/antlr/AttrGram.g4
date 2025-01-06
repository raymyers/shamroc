
grammar AttrGram;

program
    : statement* EOF
    ;

statement
    : defProd
    | defLang
    ;

defProd : 'prod' ID '(' typedId (',' typedId)* ')' ':' ID ';' ;
defLang :
    'lang' ID ('extends' ID?)
    '{'
    langDecl (',' langDecl)*
    '}' ;

langDecl
  : ID '.' ID landDeclOp nameSet;

landDeclOp : '-=' | '+=';

nameSet:
  '{' ID (',' ID)* '}' ;

typedId : ID ':' ID;

expr: ID
    | INT
    | func
    | 'not' expr
    | expr 'and' expr
    | expr 'or' expr
    ;

func : ID '(' expr (',' expr)* ')' ;


integer
    : INT
    ;

PROD
    : 'prod'
    ;

LANG
    : 'lang'
    ;


ID
    : [a-zA-Z0-9]+
    ;

INT
    : [0-9]+
    ;

WS
    : [ \r\n\t] -> skip
    ;

LBRACE: '{';
RBRACE: '}';

LPAREN: '(';
RPAREN: ')';