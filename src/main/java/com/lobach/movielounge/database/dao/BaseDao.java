package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
/**
 * The Base dao interface;
 * is used as common interface for all dao classes.
 *
 * @author Renata Lobach
 */
public interface BaseDao<T> {
    Logger logger = LogManager.getLogger();

    /**
     * This method inserts an object of type T to the database table;
     * @throws DaoException is SQLException  has been caught.
     */
    void add(T object) throws DaoException;

    /**
     * This method looks for an object of type T in the database table;
     * @param id is object id (primary key);
     * @return object if found, null if not;
     * @throws DaoException is SQLException  has been caught.
     */
    T findById(Long id) throws DaoException;

    /**
     * This method retrieves a list objects of type T in the database table;
     * @param offset is the object number to start selecting;
     * @param limit is the maximum amount of objects necessary;
     * @return list of objects (empty list if nothing found);
     * @throws DaoException is SQLException  has been caught.
     */
    List<T> findAll(int offset, int limit) throws DaoException;

    /**
     * This method removes an object of type T from the database table;
     * @param id is object id (primary key);
     * @throws DaoException is SQLException  has been caught.
     */
    void deleteById(Long id) throws DaoException;

    /**
     * This method removes all objects from the database table;
     * @param id is object id (primary key);
     * @throws DaoException is SQLException  has been caught.
     */
    void deleteAll() throws DaoException;

    /**
     * One of common methods for all dao classes;
     * is used to close statement after executing the query;
     * @param statement is the statement to close.
     */
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close statement: ", e);
        }
    }

    /**
     * One of common methods for all dao classes;
     * is used to close result set after executing the query;
     * @param resultSet is the result set to close.
     */
    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close result set: ", e);
        }
    }

    /**
     * One of common methods for all dao classes;
     * is used to close connection after executing the query;
     * @param connection is the connection to close.
     */
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
