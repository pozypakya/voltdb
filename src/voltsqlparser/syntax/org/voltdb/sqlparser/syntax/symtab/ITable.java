package org.voltdb.sqlparser.syntax.symtab;

public interface ITable extends ITop {

    /**
     * adds Column parameter newcolumn to table
     * @param newcolumn
     */
    public abstract void addColumn(String name, IColumn column);

    /**
     * form: "{---Name:<name>---,[columnname1,columntype1],[columnname2,columntype2],...}"
     */
    public abstract String toString();

    public IColumn getColumnByName(String aColumnName);

}