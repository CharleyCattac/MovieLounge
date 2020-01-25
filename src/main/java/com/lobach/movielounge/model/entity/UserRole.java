package com.lobach.movielounge.model.entity;

public enum UserRole {
    USER("user"), ADMIN("admin");

    public final String value;

    private UserRole(String value) {
        this.value = value;
    }
}
