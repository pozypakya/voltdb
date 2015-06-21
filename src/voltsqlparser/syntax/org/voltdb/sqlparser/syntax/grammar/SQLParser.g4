grammar SQLParser;


@header {
package org.voltdb.sqlparser.syntax.grammar;
}

ddl: 
        ddl_statement | dql_statement
    ;
    
dql_statement:
        select_statement
    ;
    
select_statement: 
        SELECT projection_clause
        FROM table_clause
        ( WHERE where_clause )?
    ;

projection_clause:
        '*'
    |
        projection (',' projection)*
    ;
    
projection:
        projection_ref ( ( AS )? column_name )?
    ;

projection_ref: (table_name '.' ) ? column_name
    ; 

column_ref: (table_name '.' ) ? column_name
    ; 

table_clause:
        table_ref ( ',' table_ref )* 
    ;

table_ref:
        table_name ( ( AS )? table_name )?
    ;

where_clause:
        expression
    ;
    
expression:
		'(' expression ')'
	|
		expression timesop expression
	|
		expression addop expression
	|
		expression relop expression
	|
		expression boolop expression
	|
		TRUE
	|
		FALSE
	|
		column_ref
	|
		NUMBER
	;
	
timesop:
		'*'|'/'
	;
	
addop:
		'+'|'-'
	;
	
relop:
		'='|'<'|'>'|'<='|'>='|'!='
	;

boolop: 
		'AND'|'OR'
	;

ddl_statement:
        create_table_statement
    |
        insert_statement
    ;

create_table_statement:
        CREATE TABLE table_name '(' column_definitions ')'
    ;

column_definitions:
        column_definition (',' column_definition )*
    ; 

column_definition:
        column_name type_expression  ( NOT NULL )?
    |
        constraint_definition 
    ;

constraint_definition:
        CONSTRAINT
    ;

type_expression:
        type_name ( '(' NUMBER ( ',' NUMBER )* ')' )?
    ;

insert_statement:
        INSERT INTO table_name ( '(' column_name ( ',' column_name )? ')' )?
        VALUES values
    ;
        
column_name_list:
        ( '(' column_name ( ',' column_name )* ')' )?
    ;

values: '(' value ( ',' value )* ')';

value:
        NUMBER
    ;        

/* Names */
type_name: IDENTIFIER ;

table_name: IDENTIFIER ;

column_name: IDENTIFIER ;

alias_name: IDENTIFIER;

NUMBER: NZDIGIT ( DIGIT )* ;

CREATE: C R E A T E;
TABLE:  T A B L E;
INSERT: I N S E R T;
INTO:   I N T O;
CONSTRAINT: C O N S T R A I N T;
VALUES: V A L U E S;
SELECT: S E L E C T;
FROM: F R O M;
WHERE: W H E R E;
AS: A S;
TRUE: T R U E;
FALSE: F A L S E;
NOT: N O T;
NULL: N U L L;

IDENTIFIER: LETTER ( LETTER | DIGIT )*;

fragment
LETTER: [a-zA-Z\u0080-\u00FF_] ;

fragment
NZDIGIT: [1-9];

fragment
DIGIT: [0-9];

fragment A:('a'|'A');
fragment B:('b'|'B');
fragment C:('c'|'C');
fragment D:('d'|'D');
fragment E:('e'|'E');
fragment F:('f'|'F');
fragment G:('g'|'G');
fragment H:('h'|'H');
fragment I:('i'|'I');
fragment J:('j'|'J');
fragment K:('k'|'K');
fragment L:('l'|'L');
fragment M:('m'|'M');
fragment N:('n'|'N');
fragment O:('o'|'O');
fragment P:('p'|'P');
fragment Q:('q'|'Q');
fragment R:('r'|'R');
fragment S:('s'|'S');
fragment T:('t'|'T');
fragment U:('u'|'U');
fragment V:('v'|'V');
fragment W:('w'|'W');
fragment X:('x'|'X');
fragment Y:('y'|'Y');
fragment Z:('z'|'Z');

/* Comments */
COMMENT: '#' .*? ('\r')? '\n' -> skip;
SPACE: [ \t\n] -> skip;
