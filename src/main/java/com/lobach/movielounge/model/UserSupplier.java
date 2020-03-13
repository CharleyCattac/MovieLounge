package com.lobach.movielounge.model;

public enum UserSupplier {
    INSTANCE;

    UserSupplier() {
    }

    public User createReduced(long id, UserRole role, UserStatus status,
                              String name, String phoneNumber, String avatarUrl) {
        User user = new User();
        user.setId(id);
        user.setUserRole(role);
        user.setStatus(status);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAvatarURL(avatarUrl);
        return user;
    }

    public User createBasic(String email, String password, UserRole userRole, UserStatus status) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setStatus(status);
        user.setUserRole(userRole);
        return user;
    }

    public User createFullNoId(String email, String password, UserRole userRole, UserStatus status,
                               String name, String phoneNumber, String avatarUrl) {
        User user = this.createBasic(email, password, userRole, status);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAvatarURL(avatarUrl);
        return user;
    }

    public User createFull(long id, String email, String password, UserRole userRole, UserStatus status,
                           String name, String phoneNumber, String avatarUrl) {
        User user = this.createBasic(email, password, userRole, status);
        user.setId(id);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAvatarURL(avatarUrl);
        return user;
    }
}
