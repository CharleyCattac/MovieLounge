package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.model.MovieEventFactory;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MovieEventDaoImpl implements MovieEventDao {
    private static final String INSERT_SESSION =
            "INSERT INTO events (date,movie1_id,movie2_id,movie3_id,theme) "
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_SESSIONS =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id,theme " +
                    "FROM events ORDER BY date";
    private static final String SELECT_ALL_SESSIONS_LIMITED =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id,theme " +
                    "FROM events ORDER BY date LIMIT ?,?";
    private static final String SELECT_SESSION_BY_ID =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id,theme " +
                    "FROM events WHERE events.id=?";
    private static final String SELECT_SESSIONS_BY_MOVIE_ID =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id,theme " +
                    "FROM events WHERE movie1_id=? OR movie2_id=? OR movie3_id=? LIMIT ?,?";
    private static final String UPDATE_BOOKING_AMOUNT_BY_ID =
            "UPDATE events SET booking_amount=? WHERE events.id=?";
    private static final String UPDATE_AVAILABILITY_BY_ID =
            "UPDATE events SET available=? WHERE events.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM events WHERE events.id=?";
    private static final String DELETE_ALL = "DELETE FROM events";

    @Override
    public void add(MovieEvent object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_SESSION);
            statement.setLong(1, object.getDate().getTime());
            for (int i = 0; i < 3; i++) {
                statement.setLong(2 + i, object.getMovieIds().get(i));
            }
            statement.setString(5, object.getTheme());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to add movie event: %s", e.getMessage()));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateAvailabilityById(Long id, boolean status) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_AVAILABILITY_BY_ID);
            statement.setBoolean(1, status);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update availability by id %d: %s", id, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateParticipantAmountById(Long id, int newAmount) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_BOOKING_AMOUNT_BY_ID);
            statement.setInt(1, newAmount);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update booking amount by id %d: %s", id, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to delete movie event by id %d: ", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            throw new DaoException("Failed to delete movie events: ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public MovieEvent findById(Long idKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        MovieEvent movieEvent = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_SESSION_BY_ID);
            statement.setLong(1, idKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                Date date = new Date(resultSet.getLong(index++));
                int bookingAmount = resultSet.getInt(index++);
                boolean available = resultSet.getBoolean(index++);
                List<Long> movieIds = new ArrayList<>(3);
                for (int i = 0; i < 3; i++) {
                    movieIds.set(i, resultSet.getLong(index++));
                }
                String theme = resultSet.getString(index);
                movieEvent = MovieEventFactory.INSTANCE.createFull(id, date, movieIds,bookingAmount, available, theme);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movie event with id %d: %s", idKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvent;
    }


    @Override
    public List<MovieEvent> findByMovieId(long movieId) throws DaoException {
        List<MovieEvent> movieEvents = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_SESSIONS_BY_MOVIE_ID);
            for (int i = 1; i < 4; i++) {
                statement.setLong(i, movieId);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                Date date = new Date(resultSet.getLong(index++));
                int bookingAmount = resultSet.getInt(index++);
                boolean available = resultSet.getBoolean(index++);
                List<Long> movieIds = new ArrayList<>(3);
                for (int i = 0; i < 3; i++) {
                    movieIds.set(i, resultSet.getLong(index++));
                }
                String theme = resultSet.getString(index);
                MovieEvent movieEvent = MovieEventFactory
                        .INSTANCE.createFull(id, date, movieIds,bookingAmount, available, theme);
                movieEvents.add(movieEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie event list with id %d: %s", movieId, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvents;
    }

    @Override
    public List<MovieEvent> findAll(int offset, int limit) throws DaoException {
        List<MovieEvent> movieEvents = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (limit != 0) {
                statement = connection.prepareStatement(SELECT_ALL_SESSIONS_LIMITED);
                statement.setInt(1, offset);
                statement.setInt(2, limit);
            } else {
                statement = connection.prepareStatement(SELECT_ALL_SESSIONS);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                Date date = new Date(resultSet.getLong(index++));
                int bookingAmount = resultSet.getInt(index++);
                boolean available = resultSet.getBoolean(index++);
                List<Long> movieIds = new ArrayList<>(3);
                for (int i = 0; i < 3; i++) {
                    movieIds.add(resultSet.getLong(index++));
                }
                String theme = resultSet.getString(index);
                MovieEvent movieEvent = MovieEventFactory
                        .INSTANCE.createFull(id, date, movieIds,bookingAmount, available, theme);
                movieEvents.add(movieEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie event list: %s", e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvents;
    }
}
