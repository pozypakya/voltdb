package org.voltdb.sqlparser.syntax.grammar;

import org.voltdb.sqlparser.syntax.symtab.ITable;

public interface ICatalog {

    void addTable(ITable aCurrentlyCreatedTable);

    ITable getTableByName(String aTableName);

}
