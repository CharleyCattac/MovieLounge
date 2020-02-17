package com.lobach.movielounge.model;

import java.util.Date;

public enum BookingFactory {
    INSTANCE;

    public Booking createBasic(long userId, long eventId, int amount, boolean paid, Date date) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setMovieEventId(eventId);
        booking.setAmount(amount);
        booking.setPaid(paid);
        booking.setDate(date);
        return booking;
    }

    public Booking createFull(long id, long userId, long eventId, int amount, boolean paid, Date date) {
        Booking booking = this.createBasic(userId, eventId, amount, paid, date);
        booking.setId(id);
        return  booking;
    }
}
