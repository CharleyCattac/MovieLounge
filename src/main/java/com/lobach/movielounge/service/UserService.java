package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserRole;

public interface UserService {

    void registerUser(String eMail, String password, UserRole role, boolean active) throws ServiceException;

    void updateUserData(String email, String newName, String newPhoneNumber, String newAvatarUrl)
            throws ServiceException;

    void changeUserPassword(String email, String newPassword) throws ServiceException;

    void changeUserRole(String email, UserRole newRole) throws ServiceException;

    void changeUserStatus(String email, boolean newStatus) throws ServiceException;

    User findUserByEmail(String email) throws ServiceException;

    boolean checkIfPasswordMatchesEmail(String email, String password) throws ServiceException;
}
