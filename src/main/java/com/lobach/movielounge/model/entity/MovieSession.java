package com.lobach.movielounge.model.entity;

import java.util.Date;
import java.util.Objects;

public class MovieSession {
    private Long id;
    private Long movieId;
    private Date date;
    private Movie movie;
    private int bookingAmount;
    private boolean available;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
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
        MovieSession that = (MovieSession) o;
        return bookingAmount == that.bookingAmount &&
                available == that.available &&
                Objects.equals(id, that.id) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, date, movie, bookingAmount, available);
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", date=" + date +
                ", movie=" + movie +
                ", bookingAmount=" + bookingAmount +
                ", available=" + available +
                '}';
    }
}
