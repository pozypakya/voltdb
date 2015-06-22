package org.voltdb.regressionsuites;

import java.io.IOException;

import org.voltdb.BackendTarget;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.compiler.VoltProjectBuilder;

public class TestENG8442ExistsNotFactoringSuite extends RegressionSuite {

    public TestENG8442ExistsNotFactoringSuite(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    public void testENG8442() throws Exception {
        Client client = getClient();
        ClientResponse cr;
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (8, 'nSAFoccWXxEGXR', -3364, 7.76005886643784892343e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (9, 'nSAFoccWXxEGXR', -3364, 8.65086522017155634678e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (10, 'nSAFoccWXxEGXR', 11411, 3.49977104648325210157e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (11, 'nSAFoccWXxEGXR', 11411, 4.96260220021031761561e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (12, 'ebWfhdmIZfYhRC', NULL, 3.94021683247165688257e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (13, 'ebWfhdmIZfYhRC', NULL, 2.97950296374613898820e-02);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (14, 'ebWfhdmIZfYhRC', 23926, 8.56241324965489991605e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R1 VALUES (15, 'ebWfhdmIZfYhRC', 23926, 3.61291695704730075889e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());

        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (16, 'guxAbJqFSzYXou', 12069, 3.06888528531184978654e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (17, 'guxAbJqFSzYXou', 12069, 9.46764933514720796737e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (18, 'guxAbJqFSzYXou', -18645, 2.90483004585848747503e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (19, 'guxAbJqFSzYXou', -18645, 4.89509658603335284788e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (20, 'ClerOQlohIlIZz', 20351, 6.49910310074532593383e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (21, 'ClerOQlohIlIZz', 20351, 5.39541643935691372924e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (22, 'ClerOQlohIlIZz', NULL, 7.75089084352072466011e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc","INSERT INTO R2 VALUES (23, 'ClerOQlohIlIZz', NULL, 4.49327610653072695435e-01);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());

        cr = client.callProcedure("@AdHoc", "select * from R1 where EXISTS (select RATIO from R1 INTERSECT select RATIO from R2);");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        int rowCount = 0;
        VoltTable tbl = cr.getResults()[0];
        System.out.printf("Results:\n");
        while (tbl.advanceRow()) {
            rowCount += 1;
            System.out.printf("Radius %e\n", tbl.getDouble(3));
        }
        System.out.printf("Done.\n");
        assertEquals("Expected an empty result.", 0, rowCount);
    }
    /**
     * Build a test configurations for testing ENG-8442.
     * @return
     * @throws Exception
     */
    static public junit.framework.Test suite() {

        VoltServerConfig config = null;
        MultiConfigSuiteBuilder builder = new MultiConfigSuiteBuilder(TestENG8442ExistsNotFactoringSuite.class);
        VoltProjectBuilder project = new VoltProjectBuilder();
        final String literalSchema =
                "CREATE TABLE R1 (" +
                "        ID INTEGER NOT NULL," +
                "        DESC VARCHAR(300)," +
                "        NUM INTEGER," +
                "        RATIO FLOAT," +
                "        PRIMARY KEY (ID)" +
                "      );" +
                "      CREATE TABLE R2 (" +
                "        ID INTEGER NOT NULL," +
                "        DESC VARCHAR(300)," +
                "        NUM INTEGER," +
                "        RATIO FLOAT," +
                "        PRIMARY KEY (ID)" +
                "      );" +
                ""
                ;
        try {
            project.addLiteralSchema(literalSchema);
        } catch (IOException e) {
            assertFalse(true);
        }
        boolean success;

        config = new LocalCluster("sqlinsert-onesite.jar", 1, 1, 0, BackendTarget.NATIVE_EE_JNI);
        success = config.compile(project);
        assert(success);
        builder.addServerConfig(config);

        return builder;
    }

}
