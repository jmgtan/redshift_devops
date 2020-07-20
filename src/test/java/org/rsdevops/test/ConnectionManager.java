package org.rsdevops.test;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DEFAULT_JDBC_DRIVER_CLASS = "com.amazon.redshift.jdbc42.Driver";

    private static BoneCP dataSource;

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        if (dataSource == null) {
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(getUrl());
            config.setUsername(getUser());
            config.setPassword(getPassword());
            config.setMinConnectionsPerPartition(2);
            config.setLazyInit(true);
            dataSource = new BoneCP(config);
        }

        return dataSource.getConnection();
//
//        Class.forName(getDriverClassName());
//
//        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
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
