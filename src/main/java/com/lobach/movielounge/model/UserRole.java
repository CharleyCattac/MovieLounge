package com.lobach.movielounge.model;

public enum UserRole {
    GUEST("guest"), USER("user"), ADMIN("admin");

    public final String value;

    private UserRole(String value) {
        this.value = value;
    }
}
