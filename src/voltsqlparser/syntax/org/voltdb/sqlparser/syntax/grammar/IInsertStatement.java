package org.voltdb.sqlparser.syntax.grammar;

import org.voltdb.sqlparser.syntax.symtab.ITable;
import org.voltdb.sqlparser.syntax.symtab.IType;

/**
 * The interface to insert statements.  This will be needed later.
 *
 * @author bwhite
 */
public interface IInsertStatement {

    void addTable(ITable aTable);

    void addColumn(String aString, IType aIType, String aString2);

}
