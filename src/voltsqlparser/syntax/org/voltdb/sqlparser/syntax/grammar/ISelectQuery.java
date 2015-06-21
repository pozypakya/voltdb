package org.voltdb.sqlparser.syntax.grammar;

import java.util.List;

import org.voltdb.sqlparser.semantics.grammar.Projection;
import org.voltdb.sqlparser.semantics.symtab.IAST;
import org.voltdb.sqlparser.semantics.symtab.Neutrino;
import org.voltdb.sqlparser.syntax.symtab.ISymbolTable;
import org.voltdb.sqlparser.syntax.symtab.ITable;

/**
 * This holds all the parts of a select statement.
 *
 * @author bwhite
 */
public interface ISelectQuery {

	void addProjection(String aTableName, String aColumnName, String aAlias);

	void pushNeutrino(Neutrino aColumnNeutrino);

	Neutrino popNeutrino();

	String printProjections();

	String printNeutrinos();

	void addTable(ITable aITable, String aAlias);

	String printTables();

	boolean hasNeutrinos();

	Neutrino getColumnNeutrino(String aColumnName, String aTableName);

	List<Projection> getProjections();

    void setWhereCondition(Neutrino aRet);

    IAST getWhereCondition();

    ISymbolTable getTables();

    void setAST(IAST aMakeQueryAST);
	Neutrino getNeutrinoMath(IOperator aOperator, Neutrino aLeftoperand,
			Neutrino aRightoperand);

	Neutrino getNeutrinoCompare(IOperator aOperator, Neutrino aLeftoperand,
			Neutrino aRightoperand);

	Neutrino getNeutrinoBoolean(IOperator aOperator, Neutrino aLeftoperand,
			Neutrino aRightoperand);

}
