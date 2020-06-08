package org.jantan.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DEFAULT_JDBC_DRIVER_CLASS = "com.amazon.redshift.jdbc42.Driver";

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(getDriverClassName());

        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    private static String getDriverClassName() {
        return DEFAULT_JDBC_DRIVER_CLASS;
    }

    private static String getUrl() {
        return System.getenv("TEST_JDBC_URL");
    }

    private static String getUser() {
        return System.getenv("TEST_JDBC_USER");
    }

    private static String getPassword() {
        return System.getenv("TEST_JDBC_PASSWORD");
    }
}
