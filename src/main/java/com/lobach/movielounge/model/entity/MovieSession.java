package com.lobach.movielounge.model.entity;

import java.util.Date;
import java.util.Objects;

public class MovieSession {
    private long id;
    private Date date;
    private Movie movie;
    private int bookingAmount;
    private boolean available;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(int bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSession movieSession = (MovieSession) o;
        return id == movieSession.id &&
                bookingAmount == movieSession.bookingAmount &&
                available == movieSession.available &&
                Objects.equals(date, movieSession.date) &&
                Objects.equals(movie, movieSession.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, movie, bookingAmount, available);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date=" + date +
                ", movie=" + movie +
                ", booking_amount=" + bookingAmount +
                ", available=" + available +
                '}';
    }
}
