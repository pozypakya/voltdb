package org.voltdb.sqlparser;

import static org.junit.Assert.assertFalse;
import static org.voltdb.sqlparser.symtab.CatalogAdapterAssert.assertThat;
import static org.voltdb.sqlparser.symtab.ColumnAssert.withColumnTypeNamed;
import static org.voltdb.sqlparser.symtab.TableAssert.withColumnNamed;

import java.io.IOException;

import org.junit.Test;
import org.voltdb.sqlparser.semantics.symtab.CatalogAdapter;
import org.voltdb.sqlparser.semantics.symtab.ParserFactory;
import org.voltdb.sqlparser.syntax.SQLParserDriver;
import org.voltdb.sqlparser.syntax.grammar.DDLListener;
import org.voltdb.sqlparser.syntax.grammar.ICatalog;

public class TestWhereSelect {

	DDLListener newListener() {
		CatalogAdapter catalog = new CatalogAdapter();
		ParserFactory m_factory = new ParserFactory(catalog);
        DDLListener listener = new DDLListener(m_factory);
        return listener;
    }

	@Test
    public void testDriver1() throws IOException {
		System.out.println("Test 1");
        String ddl1 = "create table alpha ( id bigint, status bigint );select id from alpha;";
        SQLParserDriver driver = new SQLParserDriver(ddl1,null);
        DDLListener listener = newListener();
        driver.walk(listener);
        assertFalse(listener.hasErrors());
        CatalogAdapter catalog = getCatalogAdapter(listener);
        assertThat(catalog).hasTableNamed("alpha",
                                          withColumnNamed("id",
                                                   withColumnTypeNamed("bigint")),
                                          withColumnNamed("status",
                                                  withColumnTypeNamed("bigint")));
    }

    @Test
    public void testDriver2() throws IOException {
		System.out.println("Test 2");
        String ddl2 = "create table alpha ( id bigint, status smallint );create table beta (id integer, status integer);\n"
        		+ "select beta.id, status from alpha as mumble,beta where id = 200 AND status < 200;"; // as in voltdb, alpha.id in projection and in where clause are errors.
        DDLListener listener = newListener();
        SQLParserDriver driver = new SQLParserDriver(ddl2,listener);
        driver.walk(listener);
        assertFalse(listener.hasErrors());
    }

	@Test
    public void testDriver3() throws IOException {
		System.out.println("Test 3");
        String ddl3 = "create table alpha ( id bigint );create table beta (id bigint, local integer);\n"
        		+ "select id from alpha, beta;select id from alpha,beta where beta.id < 250;";
        SQLParserDriver driver = new SQLParserDriver(ddl3,null);
        DDLListener listener = newListener();
        driver.walk(listener);
        assertFalse(listener.hasErrors());
    }

	@Test
    public void testDriver4() throws IOException {
		System.out.println("Test 4");
        String ddl4 = "create table alpha ( id bigint );create table beta (id bigint, local integer);\n"
        		+ "select local, id from beta where local < 150;";
        SQLParserDriver driver = new SQLParserDriver(ddl4,null);
        DDLListener listener = newListener();
        driver.walk(listener);
        assertFalse(listener.hasErrors());
    }

	@Test
    public void testDriver5() throws IOException {
		System.out.println("Test 5");
        String ddl5 = "create table alpha ( id bigint );select id+id as alias from alpha where true = 10;";
        SQLParserDriver driver = new SQLParserDriver(ddl5,null);
        DDLListener listener = newListener();
        driver.walk(listener);
        assertFalse(listener.hasErrors());
    }

	private CatalogAdapter getCatalogAdapter(DDLListener aListener) {
	    ICatalog catalog = aListener.getCatalogAdapter();
	    assert(catalog instanceof CatalogAdapter);
	    return (CatalogAdapter)catalog;
    }
}
