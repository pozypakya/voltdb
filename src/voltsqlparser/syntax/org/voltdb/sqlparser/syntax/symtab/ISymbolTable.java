package org.voltdb.sqlparser.syntax.symtab;

public interface ISymbolTable {

    /**
     * Define an entity in the Symbol Table.
     * @param aEntity
     */
    public void define(ITop aEntity);

    public int size();

    public boolean isEmpty();

    /**
     * Get the value of a string as anything.  See {@link Top} for
     * the type and value hierarchy.
     *
     * @param aName
     * @return
     */
    public ITop get(String aName);

    /**
     * Lookup a name and try to interpret it as a Type.
     * Return null if the name does not denote a type.
     *
     * @param aName The name.
     * @return The Type denoted by aName, or else null.
     */
    public IType getType(String aName);

    /**
     * Lookup a name and try to interpret it as a value.
     * Return null if the name does not denote a value.
     *
     * @param aName The name.
     * @return The value denoted by aName, or else null.
     */
    public IValue getValue(String aName);

}