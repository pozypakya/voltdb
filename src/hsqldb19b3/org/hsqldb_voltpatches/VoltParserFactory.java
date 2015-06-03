package org.hsqldb_voltpatches;

import org.voltdb.sqlparser.grammar.ICatalog;
import org.voltdb.sqlparser.grammar.IInsertStatement;
import org.voltdb.sqlparser.grammar.ISelectQuery;
import org.voltdb.sqlparser.symtab.ITable;
import org.voltdb.sqlparser.symtab.ParserFactory;

public class VoltParserFactory extends ParserFactory {
    public VoltParserFactory(ICatalog aCatalog) {
        super(aCatalog);
        // TODO Auto-generated constructor stub
    }
    /**
     * Process a query.  Create a mess of VoltXMLElements.
     */
    @Override
    public void processQuery(ISelectQuery aSelectQuery) {
    }

    /**
     * Process a table definition. Create a pot of VoltXMLElements.
     * code.
     */
    @Override
    public void processTable(ITable aCurrentlyCreatedTable) {
    }

    /**
     * Process an insert statement. Create a gaggle of VoltXMLElements.
     * code.
     */
    @Override
    public void processInsert(IInsertStatement aInsertStatement) {
    }
}
