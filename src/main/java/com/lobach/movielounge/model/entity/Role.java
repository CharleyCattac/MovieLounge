package com.lobach.movielounge.model.entity;

public enum Role {
    USER("user"), ADMIN("admin");

    public final String value;

    private Role(String value) {
        this.value = value;
    }
}
