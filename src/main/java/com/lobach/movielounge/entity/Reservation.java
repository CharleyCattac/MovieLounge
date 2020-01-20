package com.lobach.movielounge.entity;

import java.util.Objects;

public class Reservation {
    private long id;
    private User user;
    private Session session;
    private boolean active;
    private boolean paid;

    public Reservation(User user, Session session) {
        this.user = user;
        this.session = session;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, session, active, paid);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", session=" + session +
                ", active=" + active +
                ", paid=" + paid +
                '}';
    }
}
