package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Booking;

import java.util.List;

public interface BookingDao extends BaseDao<Booking> {

    void updateAmountByBookingId(long bookingId, int newAmount) throws DaoException;

    Booking findByUserEvent(long userId, long eventId) throws DaoException;

    List<Booking> findAllByUserId(long userId) throws DaoException;

    List<Booking> findAllByEventId(long eventId) throws DaoException;

    void deleteByUserEvent(long userId, long eventId) throws DaoException;
}
