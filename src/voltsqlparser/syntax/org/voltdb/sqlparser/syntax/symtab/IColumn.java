package org.voltdb.sqlparser.syntax.symtab;

public interface IColumn extends ITop {

    public abstract IType getType();

    /**
     * return array[] = {name,type}
     * @return
     */
    public abstract String[] signature();

    /**
     * form: "{<name>,<type>}"
     */
    public abstract String toString();

}