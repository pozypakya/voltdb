package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.IType;

public class Type extends Top implements IType {
    public Type(String aName, long aNominalSize, long aMaxSize) {
        super(aName, aNominalSize, aMaxSize);

    }



    public boolean equals(Type other) {
    	return (this.getName().equals(other.getName()));
    }

    public final Type getType() {
    	return this;
    }

    public String toString() {
    	return this.getClass().toString();
    }

    public Class getSuperType() {
    	return this.getClass();
    }

}
