package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.MovieSessionDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.entity.Movie;
import com.lobach.movielounge.model.entity.MovieSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String INSERT_SESSION =
            "INSERT INTO movie_sessions (date,booking_amount,available,movie1_id,movie2_id,movie3_id) "
                    + "VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_SESSIONS =
            "SELECT movie_sessions.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM movie_sessions";
    private static final String SELECT_SESSION_BY_ID =
            "SELECT movie_sessions.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM movie_sessions WHERE movie_sessions.id=?";
    private static final String SELECT_SESSION_BY_DATE =
            "SELECT movie_sessions.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM movie_sessions WHERE date=?";
    private static final String SELECT_SESSIONS_BY_MOVIE_ID =
            "SELECT movie_sessions.id,date,booking_amount,available,movie1_id,movie2_id,movie3_id " +
                    "FROM movie_sessions WHERE movie1_id=? OR movie2_id=? OR movie3_id=?";
    private static final String UPDATE_BOOKING_AMOUNT_BY_DATE =
            "UPDATE movie_sessions SET booking_amount=? WHERE date=?";
    private static final String UPDATE_AVAILABILITY_BY_DATE =
            "UPDATE movie_sessions SET available=? WHERE date=?";
    private static final String DELETE_BY_DATE = "DELETE FROM movie_sessions WHERE date=?";

    @Override
    public void insert(MovieSession object) throws DaoException {
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
    public MovieSession selectById(Long id) throws DaoException {
        MovieSession movieSession = new MovieSession();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_SESSION_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                movieSession.setId(id);
                int index = 2;
                Date date = resultSet.getDate(index++);
                movieSession.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieSession.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieSession.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieSession.getMovieIds().set(2,movie3Id);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movieSession with id %d: %s", id, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieSession;
    }

    @Override
    public List<MovieSession> selectAll() throws DaoException {
        List<MovieSession> movieSessions = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_SESSIONS);
            while (resultSet.next()) {
                MovieSession movieSession = new MovieSession();
                int index = 1;
                Long id = resultSet.getLong(index++);
                movieSession.setId(id);
                Date date = resultSet.getDate(index++);
                movieSession.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieSession.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieSession.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieSession.getMovieIds().set(2,movie3Id);
                movieSessions.add(movieSession);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie session list: %s", e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieSessions;
    }

    @Override
    public MovieSession getByDate(Date dateKey) throws DaoException {
        MovieSession movieSession = new MovieSession();
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
                movieSession.setId(id);
                index++;
                movieSession.setDate(dateKey);
                int bookingAmount = resultSet.getInt(index++);
                movieSession.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieSession.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieSession.getMovieIds().set(2,movie3Id);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movieSession with date %s: %s", dateKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieSession;
    }

    @Override
    public List<MovieSession> getByMovieId(long movieId) throws DaoException {
        List<MovieSession> movieSessions = new ArrayList<>();
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
                MovieSession movieSession = new MovieSession();
                int index = 1;
                Long id = resultSet.getLong(index++);
                movieSession.setId(id);
                Date date = resultSet.getDate(index++);
                movieSession.setDate(date);
                int bookingAmount = resultSet.getInt(index++);
                movieSession.setBookingAmount(bookingAmount);
                boolean available = resultSet.getBoolean(index++);
                movieSession.setAvailable(available);
                long movie1Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(0,movie1Id);
                long movie2Id = resultSet.getInt(index++);
                movieSession.getMovieIds().set(1,movie2Id);
                long movie3Id = resultSet.getInt(index);
                movieSession.getMovieIds().set(2,movie3Id);
                movieSessions.add(movieSession);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie session list with id %d: %s", movieId, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movieSessions;
    }

    @Override @Deprecated
    public void update(MovieSession object) throws DaoException {
    }

    @Override
    public void updateAvailabilityByDate(Date date, boolean status) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_AVAILABILITY_BY_DATE);
            statement.setBoolean(1, status);
            statement.setDate(2, date);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update availability by date %s: %s", date, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateParticipantAmountByDate(Date date, int newAmount) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_BOOKING_AMOUNT_BY_DATE);
            statement.setInt(1, newAmount);
            statement.setDate(2, date);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update booking amount by date %s: %s", date, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override @Deprecated
    public void delete(MovieSession object) throws DaoException {
    }

    @Override
    public void deleteByDate(Date date) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(DELETE_BY_DATE);
            statement.setDate(1, date);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to delete movie session by date %s: %s", date, e));
        } finally {
            close(statement);
            close(connection);
        }
    }
}
