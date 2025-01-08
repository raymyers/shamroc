grammar Shamroc;

program
    : def* EOF
    ;

def : defLang | defSequence;

defLang : 'lang' ID '{' langItem* '}';

defSequence : 'sequence' ID '{''}';

langItem:
    (record | sumType)';'
    ;

record: ID '(' recordField (',' recordField)* ')' ;

recordField : nameOrType ( ':' nameOrType)?;

sumType : ID ':' sumTypeOption ('|' sumTypeOption)*;

sumTypeOption : record | nameOrType;

nameOrType : ID ('<' ID '>')?;

COMMENT
  :  '//' ~( '\r' | '\n' )* -> skip
  ;

BLOCKCOMMENT
    : '/*' .*? '*/' -> skip
    ;

LANG: 'lang';
SEQUENCE: 'sequence';

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


WS
    : [ \t\r\n] -> skip
    ;