package org.voltdb.sqlparser.semantics.symtab;
import java.util.Set;
import java.util.TreeMap;

import org.voltdb.sqlparser.syntax.grammar.ICatalog;
import org.voltdb.sqlparser.syntax.symtab.ITable;

public class CatalogAdapter implements ICatalog {
    private TreeMap<String,Table> tables = new TreeMap<String,Table>(String.CASE_INSENSITIVE_ORDER);

    // insert all tables from catalog into table array?

    public void addTable (ITable aTable) {
        assert(aTable instanceof Table);
        Table table = (Table)aTable;
        String name = table.getName();
        tables.put(name, table);
	}

   /**
    * Fetch a table by name.
    *
    * @param tablename
    * @return
    */
	public Table getTableByName(String tablename) {
	    return tables.get(tablename);
	}

	public void printTables() {
		tables.toString();
	}

	public Set<String> getTableNames() {
	    return tables.keySet();
	}

}
