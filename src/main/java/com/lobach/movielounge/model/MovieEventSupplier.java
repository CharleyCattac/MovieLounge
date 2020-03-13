package com.lobach.movielounge.model;


import java.util.Date;
import java.util.List;

public enum MovieEventSupplier {
    INSTANCE;

    public MovieEvent createBasic(Date date, List<Long> movieIds,
                                  int bookingAmount, boolean available,
                                  String theme) {
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setDate(date);
        movieEvent.setMovieIds(movieIds);
        movieEvent.setBookingAmount(bookingAmount);
        movieEvent.setAvailable(available);
        movieEvent.setTheme(theme);
        return movieEvent;
    }

    public MovieEvent createFull(long id, Date date, List<Long> movieIds,
                                 int bookingAmount, boolean available,
                                 String theme) {
        MovieEvent movieEvent = this.createBasic(date, movieIds, bookingAmount, available, theme);
        movieEvent.setId(id);
        return movieEvent;
    }
}
