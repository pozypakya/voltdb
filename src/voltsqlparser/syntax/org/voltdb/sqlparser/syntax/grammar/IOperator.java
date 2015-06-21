package org.voltdb.sqlparser.syntax.grammar;

public interface IOperator {
    /**
     * Get a string associate with the operation.  This could
     * be the operator used int the IAST.
     * @return
     */
    String getOperation();
    /**
     * Is the operator arithmetical?
     *
     * @return
     */
    public boolean isArithmetic();
    /**
     * Is the operator relational, such as "<" or "="?
     *
     * @return
     */
    public boolean isRelational();
    /**
     * Is the operator Boolean, such as "and" or "or".
     *
     * @return
     */
    public boolean isBoolean();
}
