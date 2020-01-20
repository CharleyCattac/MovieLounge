package com.lobach.movielounge.entity;

import java.util.Date;
import java.util.Objects;

public class Session {
    private long id;
    private Date date;
    private Movie movie;
    private int booking_amount;
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

    public int getBooking_amount() {
        return booking_amount;
    }

    public void setBooking_amount(int booking_amount) {
        this.booking_amount = booking_amount;
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
        Session session = (Session) o;
        return id == session.id &&
                booking_amount == session.booking_amount &&
                available == session.available &&
                Objects.equals(date, session.date) &&
                Objects.equals(movie, session.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, movie, booking_amount, available);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date=" + date +
                ", movie=" + movie +
                ", booking_amount=" + booking_amount +
                ", available=" + available +
                '}';
    }
}
