package com.lobach.movielounge.model;

import java.util.Date;
import java.util.Objects;

public class Booking {
    private long id;
    private long userId;
    private long movieEventId;
    private User user;
    private MovieEvent movieEvent;
    private boolean paid;
    private int amount;
    private Date date;

    Booking() {
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

    public Long getMovieEventId() {
        return movieEventId;
    }

    public void setMovieEventId(Long movieEventId) {
        this.movieEventId = movieEventId;
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

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id &&
                userId == booking.userId &&
                movieEventId == booking.movieEventId &&
                paid == booking.paid &&
                amount == booking.amount &&
                Objects.equals(user, booking.user) &&
                Objects.equals(movieEvent, booking.movieEvent) &&
                Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieEventId, user, movieEvent, paid, amount, date);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieEventId=" + movieEventId +
                ", user=" + user +
                ", movieEvent=" + movieEvent +
                ", paid=" + paid +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
