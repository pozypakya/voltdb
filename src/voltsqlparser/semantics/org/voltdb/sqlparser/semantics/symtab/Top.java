package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.symtab.ITop;

/**
 * Top is the implementation of ITop.
 *
 * @author bwhite
 *
 */
public class Top implements ITop {
    long m_nominalSize;
    long m_maxSize;
    String m_name;

    Top(String aName, long aNominalSize, long aMaxSize) {
        m_name = aName;
        m_nominalSize = aNominalSize;
        m_maxSize = aMaxSize;
    }
    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ITop#getNominalSize()
     */
    @Override
    public final long getNominalSize() {
        return m_nominalSize;
    }
    public final void setNominalSize(long aNominalSize) {
        m_nominalSize = aNominalSize;
    }
    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ITop#getMaxSize()
     */
    @Override
    public final long getMaxSize() {
        return m_maxSize;
    }
    public final void setMaxSize(long aMaxSize) {
        m_maxSize = aMaxSize;
    }
    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ITop#getName()
     */
    @Override
    public final String getName() {
        return m_name;
    }
    public final void setName(String aName) {
        m_name = aName;
    }

    public String toString() {
    	return m_name.toUpperCase();
    }

}
