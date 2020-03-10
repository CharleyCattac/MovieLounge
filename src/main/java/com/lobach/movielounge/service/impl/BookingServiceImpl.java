package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.database.dao.BookingDao;
import com.lobach.movielounge.database.dao.impl.BookingDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Booking;
import com.lobach.movielounge.model.BookingFactory;
import com.lobach.movielounge.service.BookingService;

import java.util.Date;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static final String MESSAGE_INVALID_AMOUNT = "Invalid booking amount cannot be set";

    private BookingDao dao;

    public BookingServiceImpl() {
        dao = new BookingDaoImpl();
    }

    @Override
    public void makeNewBooking(long userId, long eventId, int amount) throws ServiceException {
        if (amount < 0) {
            throw new ServiceException(MESSAGE_INVALID_AMOUNT);
        }
        try {
            Booking booking = BookingFactory.INSTANCE
                    .createBasic(userId, eventId, amount, false, new Date(System.currentTimeMillis()));
            dao.add(booking);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Booking findBookingByIds(long userId, long eventId) throws ServiceException {
        try {
            return dao.findByUserEvent(userId, eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void increaseAmount(long bookingId, int currentAmount, int add) throws ServiceException {
        try {
            dao.updateAmountByBookingId(bookingId, currentAmount + add);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void decreaseAmount(long bookingId, int currentAmount, int sub) throws ServiceException {
        try {
            dao.updateAmountByBookingId(bookingId, currentAmount - sub);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllUserBookings(long userId) throws ServiceException {
        try {
            return dao.findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllEventBookings(long eventId) throws ServiceException {
        try {
            return dao.findAllByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookingById(long bookingId) throws ServiceException {
        try {
            dao.deleteById(bookingId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookingByUserEventIds(long userId, long eventI) throws ServiceException {
        try {
            dao.deleteByUserEvent(userId, eventI);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
