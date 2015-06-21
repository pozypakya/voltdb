package org.voltdb.sqlparser.semantics.grammar;

import java.util.ArrayList;
import java.util.List;

import org.voltdb.sqlparser.syntax.grammar.IInsertStatement;
import org.voltdb.sqlparser.syntax.symtab.ITable;
import org.voltdb.sqlparser.syntax.symtab.IType;
import org.voltdb.sqlparser.semantics.symtab.Type;

public class InsertStatement implements IInsertStatement, IDQLStatement {
    String m_tableName;
    List<String> m_colNames = new ArrayList<String>();
    List<Type> m_colTypes = new ArrayList<Type>();
    List<String> m_colVals = new ArrayList<String>();

    @Override
    public void addTable(ITable aTable) {
        m_tableName = aTable.getName();
    }

    @Override
    public void addColumn(String aColName, IType aType, String aValue) {
        m_colNames.add(aColName);
        assert(aType instanceof Type);
        m_colTypes.add((Type)aType);
        m_colVals.add(aValue);
    }

    public int getNumberColumns() {
        return m_colNames.size();
    }

    public String getColumnName(int idx) {
        return m_colNames.get(idx);
    }

    public Type getColumnType(int idx) {
        return m_colTypes.get(idx);
    }

    public String getColumnValue(int idx) {
        return m_colVals.get(idx);
    }

    public String getTableName() {
        return m_tableName;
    }
}
