package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.database.dao.BookingDao;
import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.dao.UserDao;
import com.lobach.movielounge.database.dao.impl.BookingDaoImpl;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.database.dao.impl.UserDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.mail.MailThread;
import com.lobach.movielounge.model.Booking;
import com.lobach.movielounge.model.BookingSupplier;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.service.BookingService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private static final String MESSAGE_INVALID_AMOUNT = "Invalid booking amount cannot be set";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private BookingDao bookingDao;
    private UserDao userDao;
    private MovieEventDao eventDao;

    public BookingServiceImpl() {
        bookingDao = new BookingDaoImpl();
        userDao = new UserDaoImpl();
        eventDao = new MovieEventDaoImpl();
    }

    @Override
    public void makeNewBooking(long userId, long eventId, int amount) throws ServiceException {
        if (amount < 0) {
            throw new ServiceException(MESSAGE_INVALID_AMOUNT);
        }
        try {
            Booking booking = BookingSupplier.INSTANCE
                    .createBasic(userId, eventId, amount, false);
            User user = userDao.findById(userId);
            MovieEvent event = eventDao.findById(eventId);
            bookingDao.add(booking);
            setEmail(user.getEmail(), event.getDate());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Booking findBookingByIds(long userId, long eventId) throws ServiceException {
        try {
            return bookingDao.findByUserEvent(userId, eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void increaseAmount(long bookingId, int currentAmount, int add) throws ServiceException {
        try {
            bookingDao.updateAmountByBookingId(bookingId, currentAmount + add);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void decreaseAmount(long bookingId, int currentAmount, int sub) throws ServiceException {
        try {
            bookingDao.updateAmountByBookingId(bookingId, currentAmount - sub);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllUserBookings(long userId) throws ServiceException {
        try {
            return bookingDao.findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllEventBookings(long eventId) throws ServiceException {
        try {
            return bookingDao.findAllByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllBookings(int offset, int limit) throws ServiceException {
        try {
            return bookingDao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookingById(long bookingId) throws ServiceException {
        try {
            bookingDao.deleteById(bookingId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookingByUserEventIds(long userId, long eventI) throws ServiceException {
        try {
            bookingDao.deleteByUserEvent(userId, eventI);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void setEmail(String email, Date eventDate) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String stringDate = format.format(eventDate);
        new MailThread(stringDate, email).start();
    }
}
