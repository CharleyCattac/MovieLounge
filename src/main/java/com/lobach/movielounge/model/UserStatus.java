package com.lobach.movielounge.model;

public enum UserStatus {
    BANNED("banned"), ACTIVE("active");

    public final String value;

    private UserStatus(String value) {
        this.value = value;
    }
}
