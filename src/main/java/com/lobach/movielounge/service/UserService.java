package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerUser(String email, String password, String name, String phoneNumber, String avatarUrl)
            throws ServiceException;

    void updateUserData(long id, String newEmail, String newName, String newPhoneNumber, String newAvatarUrl)
            throws ServiceException;

    void changeUserPassword(long id, String newPassword) throws ServiceException;

    void changeUserRole(long id, String newRole) throws ServiceException;

    void changeUserStatus(long id, String userStatus) throws ServiceException;

    User findUserById(long id) throws ServiceException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException;

    List<User> findAllUsers(int offset, int limit) throws ServiceException;
}
