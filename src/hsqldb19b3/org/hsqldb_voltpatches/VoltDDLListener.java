package org.hsqldb_voltpatches;

import org.voltdb.sqlparser.grammar.DDLListener;
import org.voltdb.sqlparser.grammar.ErrorMessage;
import org.voltdb.sqlparser.grammar.IInsertStatement;
import org.voltdb.sqlparser.grammar.ISelectQuery;
import org.voltdb.sqlparser.grammar.InsertStatement;
import org.voltdb.sqlparser.symtab.CatalogAdapter;
import org.voltdb.sqlparser.symtab.ParserFactory;

public class VoltDDLListener extends DDLListener {

    public VoltDDLListener(ParserFactory aFactory) {
        super(aFactory);
    }

    public VoltXMLElement getVoltXML() {
        IInsertStatement istat = getInsertStatement();
        if (istat != null) {
            return getVoltXML(istat);
        }
        ISelectQuery qstat = getSelectQuery();
        if (istat != null) {
            return getVoltXML(qstat);
        }
        return null;
    }

    private VoltXMLElement getVoltXML(IInsertStatement aInsertStatement) {
        assert(aInsertStatement instanceof InsertStatement);
        InsertStatement insertStatement = (InsertStatement)aInsertStatement;
        VoltXMLElement top = new VoltXMLElement("insert");
        top.withValue("table", insertStatement.getTableName());
        VoltXMLElement columns = new VoltXMLElement("columns");
        top.children.add(columns);
        for (int idx = 0; idx < insertStatement.getNumberColumns(); idx += 1) {
            VoltXMLElement col = new VoltXMLElement("column");
            columns.children.add(col);
            col.withValue("name", insertStatement.getColumnName(idx));
            VoltXMLElement val = new VoltXMLElement("value");
            col.children.add(val);
            val.withValue("id", Integer.toString(idx+1));
            val.withValue("value", insertStatement.getColumnValue(idx));
            val.withValue("valuetype", insertStatement.getColumnType(idx).getName());
        }
        VoltXMLElement params = new VoltXMLElement("parameters");
        params.withValue("name", "parameters");
        top.children.add(params);
        return top;
    }

    private VoltXMLElement getVoltXML(ISelectQuery qstat) {
        // TODO Auto-generated method stub
        return null;
    }


}
