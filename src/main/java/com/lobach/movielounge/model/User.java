package com.lobach.movielounge.model;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String email;
    private String password;
    private UserStatus status;
    private UserRole userRole;

    private String name;
    private String phoneNumber;
    private String avatarURL;

    private List<Booking> bookings;

    User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public List<Booking> getBookings() {
        return List.copyOf(bookings);
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                status == user.status &&
                userRole == user.userRole &&
                Objects.equals(name, user.name) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(avatarURL, user.avatarURL) &&
                Objects.equals(bookings, user.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, status, userRole, name, phoneNumber, avatarURL, bookings);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", userRole=" + userRole +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", reservations=" + bookings +
                '}';
    }
}
