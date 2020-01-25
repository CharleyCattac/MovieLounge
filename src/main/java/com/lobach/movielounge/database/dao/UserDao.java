package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.entity.User;

public interface UserDao extends BaseDao<User> {
    void updatePassword(String email, String newPassword) throws DaoException;
    void updateStatus(String email, Boolean newStatus) throws DaoException;
    User selectById(Long key) throws DaoException;
    User selectByEmail(String emailKey) throws DaoException;
}
