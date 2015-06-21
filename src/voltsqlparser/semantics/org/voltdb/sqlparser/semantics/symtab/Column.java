package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.IColumn;

public class Column extends Top implements IColumn {
	protected Type m_type;


	public Column(String name,Type type) {
	    super(name, 0, 0);
		this.m_type=type;
	}

	/* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.IColumn#getType()
     */
	@Override
    public final Type getType() {
        return m_type.getType();
    }


    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.IColumn#signature()
     */
	@Override
    public String[] signature() {
		return new String[]{m_name,m_type.toString()};
	}
	/* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.IColumn#toString()
     */
	@Override
    public String toString() {
		String str = "["+m_name+","+m_type.getName()+"]";
		return str;
	}

}
