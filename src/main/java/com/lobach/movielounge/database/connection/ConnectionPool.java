package com.lobach.movielounge.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    private BlockingQueue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> occupiedConnections;
    private int poolSize;

    ConnectionPool() {
    }

    public void setUpPool() {
        if (!connectionManager.init()) {
            logger.fatal("Failed to set up connection pool");
            return;
        }
        poolSize = connectionManager.getPoolSize();
        availableConnections = new LinkedBlockingDeque<>(poolSize);
        occupiedConnections = new ArrayDeque<>();
        try {
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection = connectionManager.createConnection();
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

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(String.format("Failed to retrieve connection: %s", e.getMessage()));
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection)  {
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

}