package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.IValue;

public class Value extends Top implements IValue {
    public Value(String aName, long aNominalSize, long aMaxSize) {
        super(aName, aNominalSize, aMaxSize);
    }
}
