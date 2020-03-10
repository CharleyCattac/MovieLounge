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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Connection Pool class;
 * realised as an enum in order to start together with the application
 * (non-lazy initialisation).
 *
 * @author Renata Lobach
 */

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionManager connectionManager = new ConnectionManager();

    private static AtomicBoolean isSet = new AtomicBoolean(false);
    private static AtomicBoolean isDestroyed = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

    private BlockingQueue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> occupiedConnections;
    private int poolSize;


    ConnectionPool() {
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getAvailableConnectionsCount() {
        return availableConnections.size();
    }

    public int getOccupiedConnectionsCount() {
        return occupiedConnections.size();
    }

    /**
     * This method sets up the pool creating necessary amount of connections;
     * if method fails then whole application fails too.
     */
    public void setUpPool() {
        lock.lock();
        if (!isSet.get()) {
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
            isSet.set(true);
            logger.info("Connection pool is set up");
        }
        lock.unlock();
    }

    /**
     * This method destroys pool by closing all connections and performing drivers deregistration;
     * if method fails then some of used connections would stay open
     * (but application closes anyway).
     */
    public void destroyPool() {
        lock.lock();
        if (!isDestroyed.get()) {
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
            isDestroyed.set(true);
            logger.info("Connection pool is destroyed");
        }
        lock.unlock();
    }

    public ProxyConnection occupyConnection() {
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
        if (occupiedConnections.contains(connection)) {
            occupiedConnections.remove(connection);
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                if (!availableConnections.offer(connection)) {
                    logger.error("Failed to return connection to the pool");
                }
            } catch (SQLException e) {
                logger.error(String.format("Cant set auto commit mode to true on connection: %s", connection));
                ProxyConnection newConnection = recreateConnection(connection);
                if (!availableConnections.offer(newConnection)) {
                    logger.error("Failed to add recreated connection to the pool");
                }
            }
        } else {
            throw new PoolException("Connection is not from the pool");
        }
    }

    private ProxyConnection recreateConnection(ProxyConnection invalidConnection) {
        try {
            invalidConnection.reallyClose();
            return connectionManager.createConnection();
        } catch (SQLException e) {
            throw new PoolException("Unable to recreate connection");
        }
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