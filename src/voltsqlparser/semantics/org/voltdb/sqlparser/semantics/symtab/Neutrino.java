package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.grammar.INeutrino;

public class Neutrino implements INeutrino {

	private Type m_type;
	private IAST m_ast;

	@Override
	public boolean isBooleanExpression() {
		if (m_type.getClass() == BooleanType.class)
			return true;
		else
			return false;
	}

	public Neutrino (Type newtype, IAST xml) {
		this.m_type = newtype;
		this.m_ast = xml;
	}

	public Type getType() {
		return m_type;
	}

	public Class<?> getSuperType() {
		return m_type.getSuperType();
	}

	public IAST getAST() {
	    return m_ast;
	}

}
