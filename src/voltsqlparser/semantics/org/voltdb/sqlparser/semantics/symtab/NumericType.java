package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.INumericType;

public class NumericType extends Type implements INumericType {
    public NumericType(String aName, long aNominalSize, long aMaxSize) {
        super(aName, aNominalSize, aMaxSize);
    }
}
