package com.lobach.movielounge.model.entity;

import com.lobach.movielounge.database.dao.BaseDao;

import java.util.Objects;

public class Reservation {
    private Long id;
    private Long userId;
    private Long movieSessionId;
    private User user;
    private MovieSession movieSession;
    private Boolean active;
    private Boolean paid;

    public Reservation(User user, MovieSession movieSession) {
        this.user = user;
        this.movieSession = movieSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
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
                Objects.equals(movieSession, that.movieSession) &&
                Objects.equals(active, that.active) &&
                Objects.equals(paid, that.paid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieSessionId, user, movieSession, active, paid);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieSessionId=" + movieSessionId +
                ", user=" + user +
                ", movieSession=" + movieSession +
                ", active=" + active +
                ", paid=" + paid +
                '}';
    }
}
