package com.lobach.movielounge.database.connection;

import com.lobach.movielounge.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionManager connectionManager = new ConnectionManager();

    private BlockingQueue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> occupiedConnections;
    private int poolSize;

    ConnectionPool() { // FIXME: 03/02/2020  locker + atomic boolean!!!
    }

    public void setUpPool() {
        poolSize = connectionManager.getPoolSize();
        availableConnections = new LinkedBlockingDeque<>(poolSize);
        occupiedConnections = new ArrayDeque<>();
        for (int i = 0; i < poolSize; i++) {
            try {
                ProxyConnection connection = connectionManager.createConnection();
                if (!availableConnections.offer(connection)) {
                    logger.error("Failed to add connection to the pool");
                    throw new PoolException();
                }
            } catch (SQLException e) {
                logger.fatal("Failed to fill the pool: ", e);
                throw new PoolException();
            }
        }
        logger.info("Connection pool is set up");
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Failed to retrieve connection: ", e);
            Thread.currentThread().interrupt();
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
                availableConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Failed to retrieve connection: ", e);
            } catch (SQLException e) {
                logger.error("Failed to close connection: ", e);
            }
        }
        deregisterDrivers();
        logger.info("Connection pool is destroyed");
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Failed to deregister drivers: ", e);
            }
        });
    }

}