package org.voltdb.sqlparser;

import org.voltdb.sqlparser.syntax.grammar.SQLParserBaseListener;
import org.voltdb.sqlparser.syntax.grammar.SQLParserParser;
import org.voltdb.sqlparser.syntax.grammar.SQLParserParser.Create_table_statementContext;

public class TestParserDriverListener extends SQLParserBaseListener {
    boolean m_sawCreate;
    boolean m_sawInsert;
	/**
	 * {@Inheritdoc}
	 *
	 * <P>The Default Implementation Does Nothing.</P>
	 */
	@Override public void exitCreate_table_statement(Create_table_statementContext Ctx) {
        m_sawCreate = true;
    }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitInsert_statement(SQLParserParser.Insert_statementContext ctx) {
	    m_sawInsert = true;
	}
    public boolean isSawCreate() {
        return m_sawCreate;
    }
    public boolean isSawInsert() {
        return m_sawInsert;
    }

}
