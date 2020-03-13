package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Booking;

import java.util.List;
/**
 * Dao interface for {@code Booking} type.
 *
 * @author Renata Lobach
 */
public interface BookingDao extends BaseDao<Booking> {
    /**
     * This method updates the amount of bookings of certain user
     * for certain event;
     * @param bookingId is id of booking to be updated;
     * @param newAmount is the new amount to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateAmountByBookingId(long bookingId, int newAmount) throws DaoException;

    /**
     * This method looks for a certain booking;
     * @param userId is id of the user who made booking;
     * @param eventId is id of the event booking was made for;
     * @throws DaoException is SQLException  has been caught.
     */
    Booking findByUserEvent(long userId, long eventId) throws DaoException;

    /**
     * This method looks for a list of bookings made by certain user;
     * @param userId is id of the user who made booking;
     * @throws DaoException is SQLException  has been caught.
     */
    List<Booking> findAllByUserId(long userId) throws DaoException;

    /**
     * This method looks for a list of bookings made for certain event;
     * @param eventId is id of the event booking was made for;
     * @throws DaoException is SQLException  has been caught.
     */
    List<Booking> findAllByEventId(long eventId) throws DaoException;

    /**
     * This method removes a booking;
     * @param userId is id of the user who made booking;
     * @param eventId is id of the event booking was made for;
     * @throws DaoException is SQLException  has been caught.
     */
    void deleteByUserEvent(long userId, long eventId) throws DaoException;
}
