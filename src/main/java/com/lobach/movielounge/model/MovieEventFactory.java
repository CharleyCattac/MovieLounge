package com.lobach.movielounge.model;

import java.sql.Date;
import java.util.List;

public enum MovieEventFactory {
    INSTANCE;

    public MovieEvent create(Date date, List<Long> movieIds,
                             int bookingAmount, boolean available) {
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setDate(date);
        movieEvent.setMovieIds(movieIds);
        movieEvent.setBookingAmount(bookingAmount);
        movieEvent.setAvailable(available);
        return movieEvent;
    }

    public MovieEvent createFull(long id, Date date, List<Long> movieIds,
                                 int bookingAmount, boolean available) {
        MovieEvent movieEvent = this.create(date, movieIds, bookingAmount, available);
        movieEvent.setId(id);
        return movieEvent;
    }
}
