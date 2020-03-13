package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Booking;

import java.util.List;

public interface BookingService {

    void makeNewBooking(long userId, long eventId, int amount) throws ServiceException;

    Booking findBookingByIds(long userId, long eventId) throws ServiceException;

    void increaseAmount(long bookingId, int currentAmount, int add) throws ServiceException;

    void decreaseAmount(long bookingId, int currentAmount, int sub) throws ServiceException;

    List<Booking> findAllUserBookings(long userId) throws ServiceException;

    List<Booking> findAllEventBookings(long eventId) throws ServiceException;

    List<Booking> findAllBookings(int offset, int limit) throws ServiceException;

    void deleteBookingById(long bookingId) throws ServiceException;

    void deleteBookingByUserEventIds(long userId, long eventId) throws ServiceException;
}
