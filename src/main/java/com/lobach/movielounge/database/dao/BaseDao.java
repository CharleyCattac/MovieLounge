package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao<T, K> {
    Logger logger = LogManager.getLogger();

    void add(T object) throws DatabaseException;
    void update(T object) throws DatabaseException;
    void remove(T object);
    T get(K key) throws DatabaseException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close statement due to: %s", e.getMessage()));
        }
    }

    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close result set due to: %s", e.getMessage()));
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close connection due to: %s", e.getMessage()));
        }
    }
}
