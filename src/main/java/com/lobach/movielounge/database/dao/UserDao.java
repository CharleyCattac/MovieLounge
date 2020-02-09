package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.User;

public interface UserDao extends BaseDao<User> {

    void updateById(long id, String newEmail, String newName, String newPhoneNumber, String newAvatarUrl)
            throws DaoException;

    void updatePassword(long id, String newPassword) throws DaoException;

    void updateStatus(long id, String newStatus) throws DaoException;

    void updateRole(long id, String newRole) throws DaoException;

    User findByEmailPassword(String email, String password) throws DaoException;
}
