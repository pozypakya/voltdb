package org.voltdb.voltdbsql;

import junit.framework.TestCase;


import org.hsqldb_voltpatches.HSQLInterface;
import org.hsqldb_voltpatches.HSQLInterface.HSQLParseException;
import org.hsqldb_voltpatches.VoltXMLElement;

public class TestVoltSQLParser extends TestCase {

    public void testCreateTable() {
        String ddl1 = "create table alpha ( id bigint, local integer);";
        String ddl2 = "create table beta  ( id bigint, mumbler tinyint);";
        HSQLInterface hsql = HSQLInterface.loadHsqldb();
        try {
            hsql.runDDLCommand(ddl1);
            hsql.runDDLCommand(ddl2);
            VoltXMLElement xml = hsql.getVoltCatalogXML(null);
            assertTrue(true);
        }
        catch (HSQLParseException e) {
            fail(e.getMessage());
            return;
        }
    }

}
