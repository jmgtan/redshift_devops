package org.jantan.test;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTableTest {
    private static Connection conn = null;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException {
        if (conn == null)
            conn = ConnectionManager.createConnection();
    }

    @Test
    @Order(1)
    public void testTableExists() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(*) from SVV_TABLES where table_schema=? and table_name=?");

        ps.setString(1, "public");
        ps.setString(2, "users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int rowCount = rs.getInt(1);

        assertEquals(1, rowCount);
        rs.close();
    }

    @Test
    @Order(2)
    public void testDataLoad() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("copy users from '"+getTestDataLocation()+"' csv iam_role '"+getRedshiftIAMRole()+"' ignoreheader 1");
        stmt.close();

        PreparedStatement psCount = conn.prepareStatement("select count(*) from users");
        ResultSet rs = psCount.executeQuery();

        rs.next();

        int rowCount = rs.getInt(1);

        assertEquals(1000, rowCount);

        rs.close();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        try {
            conn.prepareStatement("truncate users").executeUpdate();
        } finally {
            conn.close();
        }
    }

    private String getTestDataLocation() {
        return "s3://"+System.getenv("TEST_DATA_S3_BUCKET")+"/users_unittest_data.csv";
    }

    private String getRedshiftIAMRole() {
        return System.getenv("TEST_REDSHIFT_IAM_ROLE");
    }
}
