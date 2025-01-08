grammar Shamroc;

program
    : defLang* EOF
    ;

def : defLang;

defLang : LANG ID '{' langItem* '}';

langItem:
    (record | sumType)';'
    ;

record: ID '(' recordArg (',' recordArg)* ')' ;

recordArg : nameOrType ( ':' nameOrType)?;

sumType : ID ':' sumTypeOption ('|' sumTypeOption)*;

sumTypeOption : record | nameOrType;

nameOrType : ID ('<' ID '>')?;


LANG: 'lang';


ID : [a-zA-Z_][a-zA-Z_0-9]*;


LBrace: '{';
RBrace: '}';
LParen: '(';
RParen: ')';
Semi: ';';
Colon: ':';
Pipe: '|';
LT: '<';
RT: '>';

BLOCKCOMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n] -> skip
    ;