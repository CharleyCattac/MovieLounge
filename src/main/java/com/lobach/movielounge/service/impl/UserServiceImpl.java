package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.service.UserService;
import com.lobach.movielounge.database.dao.UserDao;
import com.lobach.movielounge.database.dao.impl.UserDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.UserFactory;

public enum UserServiceImpl implements UserService {
    INSTANCE;

    private UserDao dao;

    UserServiceImpl() {
        dao = new UserDaoImpl();
    }

    @Override
    public void registerUser(String eMail, String password, UserRole role, boolean active) throws ServiceException {
        User user = UserFactory.INSTANCE.createBasic(eMail, password, role, active);
        try {
            dao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserData(String email, String newName, String newPhoneNumber, String newAvatarUrl) throws ServiceException {
        try {
            dao.updateByEmail(email, newName, newPhoneNumber, newAvatarUrl);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserPassword(String email, String newPassword) throws ServiceException {
        try {
            dao.updatePassword(email, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserRole(String email, UserRole newRole) throws ServiceException {
        try {
            dao.updateRole(email, newRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserStatus(String email, boolean newStatus) throws ServiceException {
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
            throw new ServiceException(e);
        }
    }
}
