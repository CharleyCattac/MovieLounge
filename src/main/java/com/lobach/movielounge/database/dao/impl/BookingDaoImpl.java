package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.BookingDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private static final String INSERT =
            "INSERT INTO bookings (user_id,event_id,amount,paid,date) "
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT =
            "SELECT bookings.id,user_id,event_id,amount,paid,date FROM bookings";
    private static final String SELECT_USER_EVENT =
            "SELECT bookings.id,user_id,event_id,amount,paid,date FROM bookings WHERE user_id=%d AND event_id=%d";
    private static final String UPDATE_AMOUNT =
            "UPDATE bookings SET amount=?";
    private static final String DELETE =
            "DELETE FROM bookings";

    private static final String ORDER_BY_EVENT_ID =
            " ORDER BY (SELECT date FROM events WHERE events.id=event_id)";
    private static final String LIMIT =
            " LIMIT ?,?";
    private static final String WHERE_BOOKING_ID =
            " WHERE bookings.id=?";
    private static final String WHERE_USER_ID =
            " WHERE user_id=?";
    private static final String WHERE_EVENT_ID =
            " WHERE event_id=?";
    private static final String WHERE_USER_AND_EVENT_IDS =
            " WHERE user_id=? AND event_id=?";

    @Override
    public void add(Booking object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT);
            statement.setLong(1, object.getUserId());
            statement.setLong(2, object.getMovieEventId());
            statement.setInt(3, object.getAmount());
            statement.setBoolean(4, object.getPaid());
            statement.setLong(5, object.getDate().getTime());
            statement.executeUpdate();
            logger.info(String.format("Added new booking: %s", object.toString()));
        } catch (SQLException e) {
            throw new DaoException("Failed to add booking: ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateAmountByBookingId(long bookingId, int newAmount) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        StringBuilder queryBuilder = new StringBuilder(UPDATE_AMOUNT);
        queryBuilder.append(WHERE_BOOKING_ID);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setInt(1, newAmount);
            statement.setLong(2, bookingId);
            statement.executeUpdate();
            logger.info(String.format("Booking %d has new amount %d", bookingId, newAmount));
        } catch (SQLException e) {
            throw new DaoException("Failed to update booking amount: ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Booking findByUserEvent(long userIdKey, long eventIdKey) throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Booking booking = null;

        String existsStatement = String.format(SELECT_USER_EVENT, userIdKey, eventIdKey);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(existsStatement);
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int amount = resultSet.getInt(index++);
                boolean paid = resultSet.getBoolean(index++);
                Date date = new Date(resultSet.getLong(index));
                booking = BookingFactory.INSTANCE.createFull(id, userId, eventId, amount, paid, date);
            }
        } catch (SQLException e) {
            throw new DaoException("There's no booking with these ids: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return booking;
    }

    @Override @Deprecated
    public Booking findById(Long idKey) throws DaoException {
        /*
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Booking booking = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(WHERE_BOOKING_ID);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, idKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int amount = resultSet.getInt(index++);
                boolean paid = resultSet.getBoolean(index++);
                Date date = new Date(resultSet.getLong(index));
                booking = BookingFactory.INSTANCE.createFull(id, userId, eventId, amount, paid, date);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find booking with id %d: ", idKey), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return booking;
         */
        return null;
    }

    @Override
    public List<Booking> findAllByUserId(long userIdKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Booking> bookings = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(WHERE_USER_ID);
        queryBuilder.append(ORDER_BY_EVENT_ID);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, userIdKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int amount = resultSet.getInt(index++);
                boolean paid = resultSet.getBoolean(index++);
                Date date = new Date(resultSet.getLong(index));
                Booking booking = BookingFactory.INSTANCE.createFull(id, userId, eventId, amount, paid, date);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find bookings with user id %d: ", userIdKey), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllByEventId(long eventIdKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Booking> bookings = null;

        StringBuilder queryBuilder = new StringBuilder(SELECT);
        queryBuilder.append(WHERE_EVENT_ID);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, eventIdKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                long userId = resultSet.getLong(index++);
                long eventId = resultSet.getLong(index++);
                int amount = resultSet.getInt(index++);
                boolean paid = resultSet.getBoolean(index++);
                Date date = new Date(resultSet.getLong(index));
                Booking booking = BookingFactory.INSTANCE.createFull(id, userId, eventId, amount, paid, date);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find bookings with user id %d: ", eventIdKey), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return bookings;
    }

    @Override @Deprecated
    public List<Booking> findAll(int offset, int limit) throws DaoException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder queryBuilder = new StringBuilder(DELETE);
        queryBuilder.append(WHERE_BOOKING_ID);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to delete booking with id %d: ", id), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteByUserEvent(long userId, long eventId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder queryBuilder = new StringBuilder(DELETE);
        queryBuilder.append(WHERE_USER_AND_EVENT_IDS);
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setLong(1, userId);
            statement.setLong(2, eventId);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete booking by ids: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
    }

    @Override @Deprecated
    public void deleteAll() throws DaoException {

    }
}
