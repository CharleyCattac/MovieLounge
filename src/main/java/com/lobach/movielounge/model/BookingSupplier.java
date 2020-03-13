package com.lobach.movielounge.model;

import java.util.Date;

public enum BookingSupplier {
    INSTANCE;

    public Booking createBasic(long userId, long eventId, int amount, boolean paid) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setMovieEventId(eventId);
        booking.setAmount(amount);
        booking.setPaid(paid);
        return booking;
    }

    public Booking createFull(long id, long userId, long eventId, int amount, boolean paid) {
        Booking booking = this.createBasic(userId, eventId, amount, paid);
        booking.setId(id);
        return  booking;
    }
}
