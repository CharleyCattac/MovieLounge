package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;

import java.util.List;
import java.util.Optional;
/**
 * Service class for {@code User} type.
 *
 * @author Renata Lobach
 */
public interface UserService {
    /**
     * This method adds new user to the database if validation is successful;
     * @param email is the email of the new user;
     * @param password is the password of the new user;
     * @return error message if validation failed, null if not;
     * @throws ServiceException is SQLException  has been caught.
     */
    String registerUser(String email, String password, String name, String phoneNumber, String avatarUrl)
            throws ServiceException;
    /**
     * This method looks for a user by id;
     * @param id is user id (primary key in database);
     * @return user (possibility of null is very low);
     * @throws ServiceException is SQLException  has been caught.
     */
    User findUserById(long id) throws ServiceException;
    /**
     * This method checks if user with such credentials exists;
     * @param email is user email;
     * @param password is user password;
     * @return optional with user if found or null if not;
     * @throws ServiceException is SQLException  has been caught.
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException;
    /**
     * This method retrieves all users from the database
     * @param offset is the object number to start selecting;
     * @param limit is the maximum amount of objects necessary;
     * @return list of users (empty list if nothing found);
     * @throws ServiceException is SQLException  has been caught.
     */
    List<User> findAllUsers(int offset, int limit) throws ServiceException;

    //lower methods are unused at the current moment

    String updateUserData(long id, String newEmail, String newName, String newPhoneNumber, String newAvatarUrl)
            throws ServiceException;

    String changeUserPassword(long id, String newPassword) throws ServiceException;

    String changeUserRole(long id, String newRole) throws ServiceException;

    String changeUserStatus(long id, String userStatus) throws ServiceException;
}
