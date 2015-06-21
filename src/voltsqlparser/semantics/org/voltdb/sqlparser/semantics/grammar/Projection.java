package org.voltdb.sqlparser.semantics.grammar;

public class Projection {

	String m_tabName;
	String m_columnName;
	String m_alias;

    public Projection(String aTableName, String aColumnName, String aAlias) {
        m_tabName = aTableName;
        m_columnName = aColumnName;
        m_alias = aAlias;
    }

    public String getTableName() {
    	return m_tabName;
    }

    public String getColumnName() {
    	return m_columnName;
    }

    public String getAlias() {
    	return m_alias;
    }

    public String toString() {
    	String tab = "";
    	String alias = "";
    	if (m_tabName != null) {
    		tab = m_tabName+".";
    	} if (m_alias != null) {
    		alias = " as "+m_alias;
    	}
    	return tab+m_columnName+alias;
    }
}
