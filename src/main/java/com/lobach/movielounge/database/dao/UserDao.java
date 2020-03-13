package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.User;
/**
 * Dao interface for {@code User} type.
 *
 * @author Renata Lobach
 */
public interface UserDao extends BaseDao<User> {
    /**
     * This method updates user data;
     * @param id is id of user;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateById(long id, String newEmail, String newName, String newPhoneNumber, String newAvatarUrl)
            throws DaoException;

    /**
     * This method updates user password;
     * @param id is id of user;
     * @param newPassword is the new password to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updatePassword(long id, String newPassword) throws DaoException;

    /**
     * This method updates user status (active or banned);
     * @param id is id of user;
     * @param newStatus is the new status to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateStatus(long id, String newStatus) throws DaoException;

    /**
     * This method updates user role (admin or user);
     * @param id is id of user;
     * @param newRole is the new role to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateRole(long id, String newRole) throws DaoException;

    /**
     * This method checks if user with these credencials exists;
     * @param email is user email;
     * @param password is user password (already encrypted);
     * @return user if found;
     * @throws DaoException is SQLException  has been caught.
     */
    User findByEmailPassword(String email, String password) throws DaoException;
}
