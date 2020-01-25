package com.lobach.movielounge.model.factory;

import com.lobach.movielounge.model.entity.UserRole;
import com.lobach.movielounge.model.entity.User;

public enum UserFactory {
    INSTANCE;

    UserFactory() {
    }

    public User createBasic(String email, String password, UserRole userRole, boolean active) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(active);
        user.setUserRole(userRole);
        return user;
    }

    public User createFull(long id, String email, String password, UserRole userRole, boolean active,
                           String name, String phoneNumber, String avatarUrl) {
        User user = this.createBasic(email, password, userRole, active);
        user.setId(id);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAvatarURL(avatarUrl);
        return user;
    }
}
