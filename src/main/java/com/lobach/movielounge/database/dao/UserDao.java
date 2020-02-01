package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.User;

public interface UserDao extends BaseDao<User> {

    void updateByEmail(String email, String newName, String newPhoneNumber, String newAvatarUrl) throws DaoException;

    void updatePassword(String email, String newPassword) throws DaoException;

    void updateStatus(String email, String newStatus) throws DaoException;

    void updateRole(String email, String newRole) throws DaoException;

    User selectById(Long key) throws DaoException;

    User selectByEmail(String emailKey) throws DaoException;

    boolean passwordMatchesEmail(String email, String password) throws DaoException;
}
