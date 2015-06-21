package org.voltdb.sqlparser.semantics.symtab;

public class IntegerType extends NumericType {
    public IntegerType(String aName, long aNominalSize, long aMaxSize) {
        super(aName, aNominalSize, aMaxSize);
    }
}
