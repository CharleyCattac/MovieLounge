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
import com.lobach.movielounge.validator.URLValidator;
import com.lobach.movielounge.validator.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final String MESSAGE_BUNDLE = "message";
    private static final String MESSAGE_EMAIL = "user.email";
    private static final String MESSAGE_PASSWORD = "user.password";
    private static final String MESSAGE_NAME = "user.name";
    private static final String MESSAGE_PHONE_NUMBER = "user.phone_number";
    private static final String MESSAGE_AVATAR = "user.avatar";
    private static final String MESSAGE_ROLE = "user.role";
    private static final String MESSAGE_STATUS = "user.status";

    private UserDao dao;

    public UserServiceImpl() {
        dao = new UserDaoImpl();
    }

    @Override
    public void registerUser(String email, String password, String name, String phoneNumber, String avatarUrl)
            throws ServiceException {
        String possibleErrorMessage = registerValidation(email, password, name, phoneNumber, avatarUrl);
        if (possibleErrorMessage != null) {
            throw new ServiceException(possibleErrorMessage);
        }
        User user = UserFactory.INSTANCE
                .createFullNoId(email, password, UserRole.USER, UserStatus.ACTIVE, name, phoneNumber, avatarUrl);
        try {
            dao.add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserData(long id, String newEmail, String newName,
                               String newPhoneNumber, String newAvatarUrl) throws ServiceException {
        String possibleErrorMessage = updateDataValidation(newEmail, newName, newPhoneNumber, newAvatarUrl);
        if (possibleErrorMessage != null) {
            throw new ServiceException(possibleErrorMessage);
        }
        try {
            dao.updateById(id, newEmail, newName, newPhoneNumber, newAvatarUrl);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserPassword(long id, String newPassword) throws ServiceException {
        String possibleErrorMessage = validatePassword(newPassword);
        if (possibleErrorMessage != null) {
            throw new ServiceException(possibleErrorMessage);
        }
        try {
            dao.updatePassword(id, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserRole(long id, String newRole) throws ServiceException {
        String possibleErrorMessage = validateRole(newRole);
        if (possibleErrorMessage != null) {
            throw new ServiceException(possibleErrorMessage);
        }
        try {
            dao.updateRole(id, newRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserStatus(long id, String newStatus) throws ServiceException {
        String possibleErrorMessage = validateStatus(newStatus);
        if (possibleErrorMessage != null) {
            throw new ServiceException(possibleErrorMessage);
        }
        try {
            dao.updateStatus(id, newStatus);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserById(long id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException {
        try {
            User user = dao.findByEmailPassword(email, password);
            if (user == null) {
                return Optional.empty();
            } else {
                return Optional.of(user);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAllUsers(int offset, int limit) throws ServiceException {
        try {
            return dao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    // TODO: 08/02/2020 оставлять ли приватные мелкие валидаторы или сделать, например, внутренний класс-обертку

    private String registerValidation(String email, String password, String name,
                                      String phoneNumber, String avatarUrl) {
        String primaryResult = this.updateDataValidation(email, name, phoneNumber, avatarUrl);
        if (primaryResult != null) {
            return primaryResult;
        }
        if (!UserValidator.validatePassword(password)) {
            return MESSAGE_PASSWORD;
        }
        return null;
    }

    private String updateDataValidation(String email, String name, String phoneNumber, String avatarUrl) {
        if (!UserValidator.validateEmail(email)) {
            return MESSAGE_EMAIL;
        }
        if (!UserValidator.validateName(name)) {
            return MESSAGE_NAME;
        }
        if (!UserValidator.validatePhoneNumber(phoneNumber)) {
            return MESSAGE_PHONE_NUMBER;
        }
        if (!URLValidator.validateUrl(avatarUrl)) {
            return MESSAGE_AVATAR;
        }
        return null;
    }

    private String validatePassword(String password) {
        if (!UserValidator.validatePassword(password)) {
            return MESSAGE_PASSWORD;
        }
        return null;
    }

    private String validateRole(String roleString) {
        if (!UserValidator.validateRole(roleString)) {
            return MESSAGE_ROLE;
        }
        return null;
    }

    private String validateStatus(String statusString) {
        if (!UserValidator.validateStatus(statusString)) {
            return MESSAGE_STATUS;
        }
        return null;
    }
}
