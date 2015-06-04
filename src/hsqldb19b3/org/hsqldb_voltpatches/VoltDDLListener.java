package org.hsqldb_voltpatches;

import org.voltdb.sqlparser.grammar.DDLListener;
import org.voltdb.sqlparser.grammar.ErrorMessage;
import org.voltdb.sqlparser.symtab.CatalogAdapter;
import org.voltdb.sqlparser.symtab.ParserFactory;

public class VoltDDLListener extends DDLListener {

    public VoltDDLListener(ParserFactory aFactory) {
        super(aFactory);
    }

    public VoltXMLElement getVoltXML() {
        // TODO Auto-generated method stub
        return null;
    }
}
