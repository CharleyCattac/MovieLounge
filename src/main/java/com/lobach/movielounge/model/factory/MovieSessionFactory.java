package com.lobach.movielounge.model.factory;

import com.lobach.movielounge.model.entity.MovieSession;

import java.sql.Date;
import java.util.List;

public enum MovieSessionFactory {
    INSTANCE;

    public MovieSession create(Date date, List<Long> movieIds,
                               int bookingAmount, boolean available) {
        MovieSession session = new MovieSession();
        session.setDate(date);
        session.setMovieIds(movieIds);
        session.setBookingAmount(bookingAmount);
        session.setAvailable(available);
        return session;
    }

    public MovieSession createFull(long id, Date date, List<Long> movieIds,
                                   int bookingAmount, boolean available) {
        MovieSession session = this.create(date, movieIds, bookingAmount, available);
        session.setId(id);
        return session;
    }
}
