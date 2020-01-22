package com.lobach.movielounge.model.entity;

import java.util.Objects;

public class Reservation {
    private long id;
    private User user;
    private MovieSession movieSession;
    private boolean active;
    private boolean paid;

    public Reservation(User user, MovieSession movieSession) {
        this.user = user;
        this.movieSession = movieSession;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                active == that.active &&
                paid == that.paid &&
                Objects.equals(user, that.user) &&
                Objects.equals(movieSession, that.movieSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, movieSession, active, paid);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", session=" + movieSession +
                ", active=" + active +
                ", paid=" + paid +
                '}';
    }
}
