package org.voltdb.sqlparser.syntax.grammar;

import org.voltdb.sqlparser.semantics.symtab.IAST;
import org.voltdb.sqlparser.syntax.symtab.IType;

public interface INeutrino {
	boolean isBooleanExpression();

	public IType getType();

	public IAST getAST();
}
