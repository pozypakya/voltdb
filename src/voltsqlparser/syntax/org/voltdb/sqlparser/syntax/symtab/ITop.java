package org.voltdb.sqlparser.syntax.symtab;

/**
 * ITop is the top of the type and value hierarchy.  All
 * denotable entities, and some anonymous entities, have
 * types derived from Top.
 *
 * All ITop objects have a nominal size, a maximum size and
 * a name.  Anonymous objects have null names.  Most often,
 * the maximum and nominal sizes are identical.
 *
 * @author bwhite
 *
 */
public interface ITop {

    public abstract long getNominalSize();

    public abstract long getMaxSize();

    public abstract String getName();

}