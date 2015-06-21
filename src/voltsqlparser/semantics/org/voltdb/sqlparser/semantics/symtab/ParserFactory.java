/**
 *
 */
package org.voltdb.sqlparser.semantics.symtab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.voltdb.sqlparser.semantics.grammar.InsertStatement;
import org.voltdb.sqlparser.semantics.grammar.Projection;
import org.voltdb.sqlparser.semantics.grammar.SelectQuery;
import org.voltdb.sqlparser.syntax.grammar.ICatalog;
import org.voltdb.sqlparser.syntax.grammar.IInsertStatement;
import org.voltdb.sqlparser.syntax.grammar.IOperator;
import org.voltdb.sqlparser.syntax.grammar.ISelectQuery;
import org.voltdb.sqlparser.syntax.symtab.IColumn;
import org.voltdb.sqlparser.syntax.symtab.IParserFactory;
import org.voltdb.sqlparser.syntax.symtab.ISymbolTable;
import org.voltdb.sqlparser.syntax.symtab.ITable;
import org.voltdb.sqlparser.syntax.symtab.IType;

/**
 * @author bwhite
 *
 */
public class ParserFactory implements IParserFactory {
    ICatalog m_catalog;
    private static Map<String, IOperator> m_operatorMap = initOperatorMap();
    private Type m_booleanType = null;
    private static ISymbolTable m_stdPrelude = SymbolTable.newStandardPrelude();

    private static Map<String, IOperator> initOperatorMap() {
        HashMap<String, IOperator> answer = new HashMap<String, IOperator>();
        answer.put("+", Operator.SUM);
        answer.put("-",  Operator.MINUS);
        answer.put("*", Operator.PRODUCT);
        answer.put("/", Operator.DIVIDES);
        answer.put("=", Operator.EQUALS);
        answer.put("!=", Operator.NOT_EQUALS);
        answer.put("<", Operator.LESS_THAN);
        answer.put("<=", Operator.LESS_EQUALS);
        answer.put(">=", Operator.GREATER_EQUALS);
        answer.put(">", Operator.GREATER_THAN);
        return answer;
    }

    public ParserFactory(ICatalog aCatalog) {
        m_catalog = aCatalog;
    }
    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.IParserFactory#getStandardPrelude()
     */
    @Override
    public ISymbolTable getStandardPrelude() {
        return m_stdPrelude;
    }

    @Override
    public IColumn newColumn(String aColName, IType aColType) {
        assert(aColType instanceof Type);
        return new Column(aColName, (Type)aColType);
    }

    @Override
    public ITable newTable(String aTableName) {
        return new Table(aTableName, 0, 0);
    }

    @Override
    public ICatalog getCatalog() {
        return m_catalog;
    }

    @Override
    public ISelectQuery newSelectQuery(ISymbolTable st) {
    	assert (st instanceof SymbolTable);
    	SymbolTable symtab = (SymbolTable) st;
        return new SelectQuery(symtab, this);
    }

    /**
     * Process a query.
     */
    @Override
    public void processQuery(ISelectQuery aSelectQuery) {
        // put projections onto neutrino stack.
    	aSelectQuery.setAST(makeQueryAST(aSelectQuery.getProjections(),
    	                                 aSelectQuery.getWhereCondition(),
    	                                 aSelectQuery.getTables()));
    }

    @Override
    public IInsertStatement newInsertStatement() {
        return new InsertStatement();
    }

    @Override
    public IOperator getExpressionOperator(String aText) {
        return m_operatorMap.get(aText);
    }

    public Neutrino[] tuac(Neutrino left,Neutrino right) {
    	Type leftType = left.getType();
    	Type rightType = right.getType();
    	if (leftType.getMaxSize() == rightType.getMaxSize()) { // not comparing nominal size yet.
    		return new Neutrino[]{left,right};
    	} else if (leftType.getMaxSize() < rightType.getMaxSize()) { // right is larger
    		Neutrino converted = new Neutrino(rightType,
    		                                  addTypeConversion(left.getAST(),
    		                                                    leftType,
    		                                                    rightType));
    		return new Neutrino[]{converted,right};
    	} else { // left is larger
    		Neutrino converted = new Neutrino(leftType,
    		                                  addTypeConversion(right.getAST(),
    		                                                    rightType,
    		                                                    leftType));
    		return new Neutrino[]{left,converted};
    	}
    }

    /*
     * All of these need to be implemented in VoltDB.
     */
    @Override
    public IAST makeQueryAST(List<Projection> aProjections,
                              IAST aWhereCondition,
                              ISymbolTable aTables) {
        unimplementedOperation("makeQueryAST");
        return null;
    }

    @Override
    public IAST makeUnaryAST(IType t, boolean b) {
        unimplementedOperation("makeUnaryAST(boolean)");
        return null;
    }

    @Override
    public IAST makeUnaryAST(IType aIntType, int aValueOf) {
        unimplementedOperation("makeUnaryAST(int)");
        return null;
    }

    @Override
    public IAST makeBinaryAST(IOperator aOp,
                              Neutrino aLeftoperand,
                              Neutrino aRightoperand) {
        unimplementedOperation("makeBinaryAST");
        return null;
    }

    private void unimplementedOperation(String aFuncName) {
        throw new AssertionError("Unimplemented ParserFactory Method " + aFuncName);
    }

    @Override
    public IAST addTypeConversion(IAST aNode, IType aSrcType, IType aTrgType) {
        unimplementedOperation("addTypeConversion");
        return null;
    }

    @Override
    public IAST makeColumnRef(String aRealTableName,
                              String aTableAlias,
                              String aColumnName) {
        unimplementedOperation("makeColumnRef");
        return null;
    }

	public void processWhereExpression(Neutrino aWhereExpression) {
		// TODO implement me
	}

    @Override
    public IType makeBooleanType() {
        if (m_booleanType == null) {
            m_booleanType = new BooleanType("boolean", 0, 0);
        }
        return m_booleanType;
    }

    @Override
    public IType makeIntegerType() {
        return m_stdPrelude.getType("integer");
    }
}
