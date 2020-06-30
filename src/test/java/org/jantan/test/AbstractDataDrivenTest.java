package org.jantan.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.*;

public abstract class AbstractDataDrivenTest {
    protected static Connection conn = null;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException {
        if (conn == null)
            conn = ConnectionManager.createConnection();

        processTestDataset("users", 1000, "users.csv");
        processTestDataset("products", 50, "products.csv");
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        conn.close();
    }

    private static void processTestDataset(String table, int expectedCount, String dataFile) throws SQLException {
        if (!isTestDataValid(table, expectedCount))
            reloadData(table, dataFile);
    }

    private static boolean isTestDataValid(String table, int expectedCount) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(*) from "+table);
        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        return count > 0 && count == expectedCount;
    }

    protected static void clearTable(String table) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("truncate "+table);
        stmt.close();
    }

    private static void reloadData(String table, String dataFile) throws SQLException {
        clearTable(table);
        Statement stmt = conn.createStatement();
        stmt.execute("copy "+table+" from '"+getDataLocation(dataFile)+"' csv iam_role '"+getDataLoadIAMRole()+"' ignoreheader 1");
        stmt.close();
    }

    private static String getDataLocation(String dataFile) {
        return "s3://"+System.getenv("TEST_DATA_S3_BUCKET")+"/"+dataFile;
    }

    private static String getDataLoadIAMRole() {
        return System.getenv("TEST_REDSHIFT_IAM_ROLE");
    }
}
