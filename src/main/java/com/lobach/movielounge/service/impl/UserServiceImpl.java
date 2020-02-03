package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.UserStatus;
import com.lobach.movielounge.service.UserService;
import com.lobach.movielounge.database.dao.UserDao;
import com.lobach.movielounge.database.dao.impl.UserDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.UserFactory;
import com.lobach.movielounge.validator.UserValidator;

public class UserServiceImpl implements UserService {

    private static final String MESSAGE_BUNDLE = "properties/messages";
    private static final String MESSAGE_EMAIL = "user.invalid.email";
    private static final String MESSAGE_PASSWORD = "user.invalid.password";
    private static final String MESSAGE_NAME = "user.invalid.name";
    private static final String MESSAGE_PHONE_NUMBER = "user.invalid.phone_number";
    private static final String MESSAGE_ROLE = "user.invalid.role";
    private static final String MESSAGE_STATUS = "user.invalid.status";
    private static final String MESSAGE_MATCH = "user.invalid.email_password_do_not_match";

    private UserDao dao;

    public UserServiceImpl() {
        dao = new UserDaoImpl();
    }

    @Override
    public void registerUser(String eMail, String password) throws ServiceException {
        String validationResult = signInValidation(eMail, password);
        if (validationResult != null) {
            throw new ServiceException(validationResult);
        }
        User user = UserFactory.INSTANCE.createBasic(eMail, password, UserRole.USER, UserStatus.ACTIVE);
        try {
            dao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserData(String email, String newName,
                               String newPhoneNumber, String newAvatarUrl) throws ServiceException {
        String validationResult = updateDataValidation(newName, newPhoneNumber);
        if (validationResult != null) {
            throw new ServiceException(validationResult);
        }
        try {
            dao.updateByEmail(email, newName, newPhoneNumber, newAvatarUrl);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserPassword(String email, String newPassword) throws ServiceException {
        String validationResult = validatePassword(newPassword);
        if (validationResult != null) {
            throw new ServiceException(validationResult);
        }
        try {
            dao.updatePassword(email, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserRole(String email, String newRole) throws ServiceException {
        String validationResult = validateRole(newRole);
        if (validationResult != null) {
            throw new ServiceException(validationResult);
        }
        try {
            dao.updateRole(email, newRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserStatus(String email, String newStatus) throws ServiceException {
        String validationResult = validateStatus(newStatus);
        if (validationResult != null) {
            throw new ServiceException(validationResult);
        }
        try {
            dao.updateStatus(email, newStatus);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        try {
            return dao.selectByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkIfPasswordMatchesEmail(String email, String password) throws ServiceException {
        try {
            return dao.passwordMatchesEmail(email, password);
        } catch (DaoException e) {
            String message = PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_MATCH);
            throw new ServiceException(message);
        }
    }

    private String validatePassword(String password) {
        if (!UserValidator.validatePassword(password)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_PASSWORD);
        }
        return null;
    }

    private String validateRole(String roleString) {
        if (!UserValidator.validateRole(roleString)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_ROLE);
        }
        return null;
    }

    private String validateStatus(String statusString) {
        if (!UserValidator.validateStatus(statusString)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_STATUS);
        }
        return null;
    }

    private String signInValidation(String email, String password) {
        if (!UserValidator.validateEmail(email)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_EMAIL);
        }
        if (!UserValidator.validatePassword(password)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_PASSWORD);
        }
        return null;
    }

    private String updateDataValidation(String name, String phoneNumber) {
        if (!UserValidator.validateName(name)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_NAME);
        }
        if (!UserValidator.validatePhoneNumber(phoneNumber)) {
            return PropertyManager.getProperty(MESSAGE_BUNDLE, MESSAGE_PHONE_NUMBER);
        }
        return null;
    }
}
