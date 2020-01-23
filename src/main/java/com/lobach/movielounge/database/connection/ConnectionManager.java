package com.lobach.movielounge.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionManager {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static ResourceBundle bundle;

    private static final String BUNDLE_NAME = "startup";
    private static final String PROPERTY_DB_URL = "database.url";
    private static final String PROPERTY_DB_DRIVER = "database.driver";
    private static final String PROPERTY_DB_USER = "database.user";
    private static final String PROPERTY_DB_PASSWORD = "database.password";
    private static final String PROPERTY_DB_POOL_SIZE = "database.pool_size";

    private BlockingQueue<Connection> availableConnections;
    private Queue<Connection> occupiedConnections;
    private int poolSize;

    ConnectionManager() {
    }

    public void setUpPool() {
        if (!init()) {
            logger.fatal("Failed to set up connection pool");
            return;
        }
        availableConnections = new LinkedBlockingDeque<>(poolSize);
        occupiedConnections = new ArrayDeque<>();
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = createConnection();
                if (!availableConnections.offer(connection)) {
                    logger.error("Failed to add connection to the pool");
                }
            }
        } catch (SQLException e) {
            logger.fatal(String.format("Failed to fill the pool: %s", e));
            return;
        }
        logger.info("Connection pool is set up");
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = availableConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(String.format("Failed to retrieve connection: %s", e.getMessage()));
        }
        return connection;
    }

    public void releaseConnection(Connection connection)  {
        occupiedConnections.remove(connection);
        if (!availableConnections.offer(connection)) {
            logger.error("Failed to return connection to the pool");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < poolSize; i++) {
            try {
                availableConnections.take().close();
            } catch (InterruptedException e) {
                logger.error(String.format("Failed to retrieve connection: %s", e.getMessage()));
            } catch (SQLException e) {
                logger.error(String.format("Failed to close connection: %s", e.getMessage()));
            }
        }
        logger.info("Connection pool is destroyed");
    }

    private boolean init() {
        try {
            bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            String driverUrl = bundle.getString(PROPERTY_DB_DRIVER);
            Class.forName(driverUrl);
            String pSize = bundle.getString(PROPERTY_DB_POOL_SIZE);
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

    private Connection createConnection() throws SQLException {
        String databaseUrl = bundle.getString(PROPERTY_DB_URL);
        String userName = bundle.getString(PROPERTY_DB_USER);
        String userPassword = bundle.getString(PROPERTY_DB_PASSWORD);
        return DriverManager.getConnection(databaseUrl, userName, userPassword);
    }
}