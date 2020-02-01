package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;

public interface UserService {

    void registerUser(String eMail, String password) throws ServiceException;

    void updateUserData(String email, String newName, String newPhoneNumber, String newAvatarUrl)
            throws ServiceException;

    void changeUserPassword(String email, String newPassword) throws ServiceException;

    void changeUserRole(String email, String newRole) throws ServiceException;

    void changeUserStatus(String email, String userStatus) throws ServiceException;

    User findUserByEmail(String email) throws ServiceException;

    boolean checkIfPasswordMatchesEmail(String email, String password) throws ServiceException;
}
