package com.lobach.movielounge.command;

public enum LocaleType {
    EN("EN"),
    RU("RU"),
    BY("BY");

    public final String property;

    LocaleType(String property) {
        this.property = property;
    }
}
