package org.voltdb.sqlparser.semantics.symtab;
import java.util.*; // Uses arrayList, maybe j.u.* is too much.

import org.voltdb.sqlparser.syntax.symtab.IColumn;
import org.voltdb.sqlparser.syntax.symtab.ITable;

public class Table extends Top implements ITable {
	Map<String, Column> m_lookup = new TreeMap<String, Column>(String.CASE_INSENSITIVE_ORDER);

	public Table(String aTableName, int aMaxSize, int aNominalSize) {
	    super(aTableName, aMaxSize, aNominalSize);
	}

	/* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ITable#addColumn(java.lang.String, org.voltdb.sqlparser.symtab.Column)
     */
	@Override
    public void addColumn(String name, IColumn column) {
	    assert(column instanceof Column);
		m_lookup.put(name, (Column)column);
	}


	/* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ITable#toString()
     */
    public String toString() {
		String str = "{---Name:" + getName() + "---,";
		for (String key : m_lookup.keySet()) {
		    IColumn icol = m_lookup.get(key);
		    assert(icol instanceof Column);
			str += ((Column)icol).toString();
		}
		str += "}";
		return str;
	}

    public Column getColumnByName(String aName) {
        return m_lookup.get(aName);
    }

    public Set<String> getColumnNames() {
        return m_lookup.keySet();
    }

}
