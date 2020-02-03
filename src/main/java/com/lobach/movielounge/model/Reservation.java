package com.lobach.movielounge.model;

import java.util.Objects;

public class Reservation {
    private long id;
    private long userId;
    private long movieSessionId;
    private User user;
    private MovieEvent movieEvent;
    private boolean active;
    private boolean paid;

    public Reservation(User user, MovieEvent movieEvent) {
        this.user = user;
        this.movieEvent = movieEvent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(Long movieSessionId) {
        this.movieSessionId = movieSessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MovieEvent getMovieEvent() {
        return movieEvent;
    }

    public void setMovieEvent(MovieEvent movieEvent) {
        this.movieEvent = movieEvent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieSessionId, that.movieSessionId) &&
                Objects.equals(user, that.user) &&
                Objects.equals(movieEvent, that.movieEvent) &&
                Objects.equals(active, that.active) &&
                Objects.equals(paid, that.paid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieSessionId, user, movieEvent, active, paid);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieSessionId=" + movieSessionId +
                ", user=" + user +
                ", movieSession=" + movieEvent +
                ", active=" + active +
                ", paid=" + paid +
                '}';
    }
}
