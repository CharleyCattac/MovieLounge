package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieEventDaoImpl implements MovieEventDao {
    private static final String INSERT_SESSION =
            "INSERT INTO events (date,booking_amount,available,movie1_id,movie2_id,movie3_id) "
                    + "VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_SESSIONS =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM events ORDER BY date";
    private static final String SELECT_ALL_SESSIONS_LIMITED =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM events ORDER BY date LIMIT ?,?";
    private static final String SELECT_SESSION_BY_ID =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM events WHERE events.id=?";
    private static final String SELECT_SESSION_BY_DATE =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM events WHERE date=?";
    private static final String SELECT_SESSIONS_BY_MOVIE_ID =
            "SELECT events.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM events WHERE movie1_id=? OR movie2_id=? OR movie3_id=? LIMIT ?,?";
    private static final String UPDATE_BOOKING_AMOUNT_BY_ID =
            "UPDATE events SET booking_amount=? WHERE movie_session.id=?";
    private static final String UPDATE_AVAILABILITY_BY_ID =
            "UPDATE events SET available=? WHERE movie_session.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM events WHERE movie_session.id=?";

    @Override
    public void insert(MovieEvent object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_SESSION);
            statement.setDate(1, object.getDate());
            statement.setInt(2, object.getBookingAmount());
            statement.setBoolean(3, object.isAvailable());
            for (int i = 0; i < 3; i++) {
                statement.setLong(4 + i, object.getMovieIds().get(i));
            }
            statement.executeUpdate();
            logger.info(String.format("Added new movie session: %s", object.getDate()));
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to add movie session: %s", e.getMessage()));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public MovieEvent selectById(Long id) throws DaoException {
        MovieEvent movieEvent = new MovieEvent();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_SESSION_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                movieEvent.setId(id);
                int index = 2;
                Date date = resultSet.getDate(index++);
                movieEvent.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieEvent.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieEvent.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieEvent.getMovieIds().set(2,movie3Id);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movieSession with id %d: %s", id, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvent;
    }

    @Override
    public List<MovieEvent> selectAll(int offset, int limit) throws DaoException {
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
                MovieEvent movieEvent = new MovieEvent();
                int index = 1;
                long id = resultSet.getLong(index++);
                movieEvent.setId(id);
                Date date = resultSet.getDate(index++);
                movieEvent.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieEvent.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieEvent.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieEvent.getMovieIds().set(2,movie3Id);
                movieEvents.add(movieEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie session list: %s", e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvents;
    }

    @Override
    public MovieEvent getByDate(Date dateKey) throws DaoException {
        MovieEvent movieEvent = new MovieEvent();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_SESSION_BY_DATE);
            statement.setDate(1, dateKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                movieEvent.setId(id);
                index++;
                movieEvent.setDate(dateKey);
                int bookingAmount = resultSet.getInt(index++);
                movieEvent.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieEvent.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieEvent.getMovieIds().set(2,movie3Id);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movieSession with date %s: %s", dateKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvent;
    }

    @Override
    public List<MovieEvent> getByMovieId(long movieId) throws DaoException {
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
                MovieEvent movieEvent = new MovieEvent();
                int index = 1;
                Long id = resultSet.getLong(index++);
                movieEvent.setId(id);
                Date date = resultSet.getDate(index++);
                movieEvent.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieEvent.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieEvent.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieEvent.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieEvent.getMovieIds().set(2,movie3Id);
                movieEvents.add(movieEvent);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie session list with id %d: %s", movieId, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieEvents;
    }

    @Override @Deprecated
    public void update(MovieEvent object) throws DaoException {
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

    @Override @Deprecated
    public void delete(MovieEvent object) throws DaoException {
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
            throw new DaoException(String.format("Failed to delete movie session by id %d: %s", id, e));
        } finally {
            close(statement);
            close(connection);
        }
    }
}
