package com.lobach.movielounge.command;

public enum LocaleType {
    EN("interface"),
    RU("interface_ru"),
    BY("interface_by");

    public final String property;

    LocaleType(String property) {
        this.property = property;
    }
}
