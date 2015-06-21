package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.ITemporal;

/**
 * The class Temporal implements ITemporal.
 *
 * @author bwhite
 *
 */
public class Temporal extends Type implements ITemporal {

    public Temporal(String aName, long aNominalSize, long aMaxSize) {
        super(aName, aNominalSize, aMaxSize);
    }

}
