package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.grammar.IOperator;

public enum Operator implements IOperator {
    SUM("add",                       100),
    MINUS("subtract",                100),
    PRODUCT("multiply",              100),
    DIVIDES("divide",                100),
    EQUALS("equal",                  200),
    NOT_EQUALS("notequal",           200),
    LESS_THAN("lessthan",            200),
    LESS_EQUALS("lessthanorequalto", 200),
    GREATER_THAN("greaterthan",      200),
    GREATER_EQUALS("greaterthanorequalto",
                                     200),
    BOOLAND("and",                   300),
    BOOLOR("or",                     300),
    BOOLNOT("not",                   300);

    String m_opn;
    int m_kind;
    Operator(String aOpn, int aKind) {
        m_opn = aOpn;
        m_kind = aKind;
    }

    public String getOperation() {
        return m_opn;
    }

    public boolean isArithmetic() {
        return m_kind == 100;
    }
    public boolean isRelational() {
        return m_kind == 200;
    }
    public boolean isBoolean() {
        return m_kind == 300;
    }
}
