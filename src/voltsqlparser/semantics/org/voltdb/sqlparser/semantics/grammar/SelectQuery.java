package org.voltdb.sqlparser.semantics.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.voltdb.sqlparser.semantics.symtab.Column;
import org.voltdb.sqlparser.semantics.symtab.IAST;
import org.voltdb.sqlparser.semantics.symtab.Neutrino;
import org.voltdb.sqlparser.semantics.symtab.SymbolTable;
import org.voltdb.sqlparser.semantics.symtab.Table;
import org.voltdb.sqlparser.semantics.symtab.Type;
import org.voltdb.sqlparser.syntax.grammar.IOperator;
import org.voltdb.sqlparser.syntax.grammar.ISelectQuery;
import org.voltdb.sqlparser.syntax.symtab.IParserFactory;
import org.voltdb.sqlparser.syntax.symtab.ITable;


public class SelectQuery implements ISelectQuery, IDQLStatement {
    List<Projection> m_projections = new ArrayList<Projection>();

    private Stack<Neutrino> neutrinoStack = new Stack<Neutrino>();
    private SymbolTable m_tables;
    private Neutrino m_whereCondition;
    private IParserFactory m_factory;
    private IAST m_ast;

    public SelectQuery(SymbolTable aParent, IParserFactory aFactory) {
        m_whereCondition = null;
    	m_factory = aFactory;
    	m_tables = new SymbolTable(aParent);
    }

    public List<Projection> getProjections() {
    	return m_projections;
    }

    @Override
    public void addProjection(String aTableName, String aColumnName,
            String aAlias) {
        m_projections.add(new Projection(aTableName, aColumnName, aAlias));
    }

    @Override
    public void addTable(ITable aTable, String aAlias) {
    	if (aAlias != null)
    		m_tables.addTable((Table)aTable, aAlias);
    	else
    		m_tables.addTable((Table)aTable, aTable.getName());
    }

    public ITable getTableByName(String aName) {
        return m_tables.getTable(aName);
    }

	@Override
	public void pushNeutrino(Neutrino aColumnNeutrino) {
		neutrinoStack.push(aColumnNeutrino);
	}

	@Override
	public Neutrino popNeutrino() {
		Neutrino bottom = neutrinoStack.pop();
		return bottom;
	}

	@Override
	public Neutrino getNeutrinoMath(IOperator aOperator,
	                                   Neutrino aLeftoperand,
	                                   Neutrino aRightoperand) {
		if (!aRightoperand.getSuperType().equals(aRightoperand.getSuperType())) {
		    return null;
		} // TODO check type is correct (int,float,long,etx);

		Neutrino[] converted = m_factory.tuac(aLeftoperand, aRightoperand);


		return new Neutrino(converted[0].getType(),
		                    m_factory.makeBinaryAST(aOperator,
		                                            converted[0],
		                                            converted[1]));
	}

    @Override
    public Neutrino getNeutrinoCompare(IOperator aOperator,
                                       Neutrino aLeftoperand,
                                       Neutrino aRightoperand) {
		if (!aRightoperand.getSuperType().equals(aRightoperand.getSuperType())) {
		    return null;
		} // TODO check type is correct (int,float,long,etx);
		Neutrino[] converted = m_factory.tuac(aLeftoperand, aRightoperand);


		return new Neutrino((Type) m_factory.makeBooleanType(),
		                    m_factory.makeBinaryAST(aOperator,
		                                            converted[0],
		                                            converted[1]));
    }

	@Override
	public Neutrino getNeutrinoBoolean(IOperator aOperator,
	                                   Neutrino aLeftoperand,
	                                   Neutrino aRightoperand) {
		if (!aRightoperand.getSuperType().equals(aRightoperand.getSuperType())) {
			return null;
		}
		return new Neutrino(aRightoperand.getType(),
		                    m_factory.makeBinaryAST(aOperator,
		                                            aLeftoperand,
		                                            aRightoperand));
	}

    @Override
	public Neutrino getColumnNeutrino(String aColumnName, String aTableName) {
        String realTableName;
        String tableAlias;
        Table tbl;
        if (aTableName != null) {
            tableAlias = aTableName;
        } else {
            tableAlias = m_tables.getTableAliasByColumn(aColumnName);
        }
        tbl = m_tables.getTable(tableAlias);
        if (tbl == null) {
            return null;
        }
        realTableName = tbl.getName();
        Column col = tbl.getColumnByName(aColumnName);
        Type colType = col.getType();
        return new Neutrino(colType,
                            m_factory.makeColumnRef(realTableName,
                                                    tableAlias,
                                                    aColumnName));
	}

	@Override
	public String printProjections() {
		String out = "projections: ";
		for (int i=0;i<m_projections.size();i++) {
			out += "["+m_projections.get(i).toString()+"]";
		}
		return out;
	}

	@Override
	public String printTables() {
		String out = "Tables: ";
		out += m_tables.toString();
		return out;
	}

	@Override
	public String printNeutrinos() {
		String out = "Neutrinos: ";
		while(!neutrinoStack.isEmpty()) {
			Neutrino next = neutrinoStack.pop();
			out += "["+next.toString()+"]";
		}
		return out;
	}

	@Override
	public boolean hasNeutrinos() {
		return !neutrinoStack.isEmpty();
	}

	@Override
	public void setWhereCondition(Neutrino aNeutrino) {
	    m_whereCondition = aNeutrino;
	}

    @Override
    public IAST getWhereCondition() {
        if (m_whereCondition != null) {
            return m_whereCondition.getAST();
        }
        return null;
    }

    @Override
    public SymbolTable getTables() {
        return m_tables;
    }

    @Override
    public void setAST(IAST aMakeQueryAST) {
        m_ast = aMakeQueryAST;
    }

    public IAST getAST() {
        return m_ast;
    }

}
