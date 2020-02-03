package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T> {
    Logger logger = LogManager.getLogger();

    void insert(T object) throws DaoException;

    void update(T object) throws DaoException;

    void delete(T object) throws DaoException;

    T selectById(Long id) throws DaoException;

    List<T> selectAll(int offset, int limit) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close statement: %s", e));
        }
    }

    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close result set: %s", e));
        }
    }

    default void close(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Failed to close connection: %s", e));
        }
    }
}
