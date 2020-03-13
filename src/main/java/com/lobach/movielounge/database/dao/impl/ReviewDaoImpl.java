package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.ReviewDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao {

    private static final String INSERT =
            "INSERT INTO reviews (user_id,event_id,rate,overall_text,review_text)"
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT =
            "SELECT reviews.id,user_id,event_id,rate,overall_text,review_text FROM reviews";
    private static final String DELETE =
            "DELETE FROM reviews";
    private static final String ORDER_BY =
            " ORDER BY (SELECT date FROM events WHERE events.id=event_id)";
    private static final String LIMIT =
            " LIMIT ?,?";
    private static final String WHERE_ID =
            " WHERE reviews.id=?";
    private static final String WHERE_USER_ID =
            " WHERE user_id=%d";

    @Override
    public void add(Review object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(INSERT);
            statement.setLong(1, object.getUserId());
            statement.setLong(2, object.getMovieEventId());
            statement.setInt(3, object.getRate());
            statement.setString(4, object.getShortOverallText());
            statement.setString(5, object.getReviewText());
            statement.executeUpdate();
            logger.info(String.format("Added new review: %s", object.toString()));
        } catch (SQLException e) {
            throw new DaoException("Failed to add movie: ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Review findById(Long idKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Review review = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(WHERE_ID);
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, idKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int rate = resultSet.getInt(index++);
                String overall = resultSet.getString(index++);
                String reviewText = resultSet.getString(index);
                review =  ReviewSupplier.INSTANCE.createFull(id, userId, eventId, rate, overall, reviewText);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movie with id %d: %s", idKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return review;
    }

    @Override
    public List<Review> findAll(int offset, int limit) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(ORDER_BY);
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            if (limit != 0) {
                queryBuilder.append(LIMIT);
                statement = connection.prepareStatement(queryBuilder.toString());
                statement.setInt(1, offset);
                statement.setInt(2, limit);
            } else {
                statement = connection.prepareStatement(queryBuilder.toString());
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int rate = resultSet.getInt(index++);
                String overall = resultSet.getString(index++);
                String reviewText = resultSet.getString(index);
                Review review = ReviewSupplier.INSTANCE.createFull(id, userId, eventId, rate, overall, reviewText);
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find reviews ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return reviews;
    }

    @Override
    public List<Review> findByUserId(long userIdKey, int offset, int limit) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(String.format(WHERE_USER_ID, userIdKey));
        queryBuilder.append(ORDER_BY);
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            if (limit != 0) {
                queryBuilder.append(LIMIT);
                statement = connection.prepareStatement(queryBuilder.toString());
                statement.setInt(1, offset);
                statement.setInt(2, limit);
            } else {
                statement = connection.prepareStatement(queryBuilder.toString());
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int rate = resultSet.getInt(index++);
                String overall = resultSet.getString(index++);
                String reviewText = resultSet.getString(index);
                Review review = ReviewSupplier.INSTANCE.createFull(id, userId, eventId, rate, overall, reviewText);
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find reviews by user id %d: ", userIdKey), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return reviews;
    }

    @Override
    public void deleteById(Long idKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder queryBuilder = new StringBuilder(DELETE);
        queryBuilder.append(WHERE_ID);
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, idKey);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movie with id %d: %s", idKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DELETE);
        } catch (SQLException e) {
            throw new DaoException("Failed to find movie with id: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
    }
}
