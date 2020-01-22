package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.model.entity.User;

public class UserDao implements BaseDao<User, Long> {

    @Override
    public boolean add(User object) {
        return false;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public boolean remove(User object) {
        return false;
    }

    @Override
    public User get(Long key) {
        return null;
    }
}
