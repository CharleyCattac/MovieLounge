package com.lobach.movielounge.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MovieEvent {
    private long id;
    private Date date;
    private List<Long> movieIds;
    private List<Movie> movies;
    private int bookingAmount;
    private boolean available;
    private String theme;

    MovieEvent() {
        movieIds = new ArrayList<>(3);
        movies = new ArrayList<>(3);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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
        MovieEvent that = (MovieEvent) o;
        return bookingAmount == that.bookingAmount &&
                available == that.available &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(movieIds, that.movieIds) &&
                Objects.equals(movies, that.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, movieIds, movies, bookingAmount, available);
    }

    @Override
    public String toString() {
        return "MovieEvent{" +
                "id=" + id +
                ", date=" + date +
                ", movieIds=" + movieIds +
                ", movies=" + movies +
                ", bookingAmount=" + bookingAmount +
                ", available=" + available +
                ", theme='" + theme + '\'' +
                '}';
    }
}
