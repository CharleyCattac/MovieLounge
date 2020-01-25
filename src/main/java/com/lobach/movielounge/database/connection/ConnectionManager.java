package com.lobach.movielounge.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

enum ConnectionManager {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_NAME = "startup";
    private static final String PROPERTY_DB_URL = "database.url";
    private static final String PROPERTY_DB_DRIVER = "database.driver";
    private static final String PROPERTY_DB_USER = "database.user";
    private static final String PROPERTY_DB_PASSWORD = "database.password";
    private static final String PROPERTY_DB_POOL_SIZE = "database.pool_size";

    private String databaseUrl;
    private String userName;
    private String userPassword;
    private int poolSize;

    ConnectionManager() {
    }

    boolean init() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            databaseUrl = bundle.getString(PROPERTY_DB_URL);
            String driverUrl = bundle.getString(PROPERTY_DB_DRIVER);
            userName = bundle.getString(PROPERTY_DB_USER);
            userPassword = bundle.getString(PROPERTY_DB_PASSWORD);
            String pSize = bundle.getString(PROPERTY_DB_POOL_SIZE);

            Class.forName(driverUrl);
            poolSize = Integer.parseInt(pSize);

            return true;
        } catch (MissingResourceException e) {
            logger.fatal(String.format("Resource not found: %s", BUNDLE_NAME));
        } catch (ClassNotFoundException e) {
            logger.fatal("No drivers found by given url");
        } catch (IllegalArgumentException e) {
            logger.fatal("Invalid pool size given");
        }
        return false;
    }

    ProxyConnection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(databaseUrl, userName, userPassword));
    }

    int getPoolSize() {
        return poolSize;
    }
}
