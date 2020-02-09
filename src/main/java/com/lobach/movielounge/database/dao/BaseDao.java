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

    void add(T object) throws DaoException;

    T findById(Long id) throws DaoException;

    List<T> findAll(int offset, int limit) throws DaoException;

    void deleteById(Long id) throws DaoException;

    void deleteAll() throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close statement: ", e);
        }
    }

    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close result set: ", e);
        }
    }

    default void close(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close connection: ", e);
        }
    }
}
