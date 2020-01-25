package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.UserDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.entity.User;
import com.lobach.movielounge.model.entity.UserRole;
import com.lobach.movielounge.model.factory.UserFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public enum UserDaoImpl implements UserDao {
    INSTANCE;

    private static final String INSERT_USER =
                    "INSERT INTO users (email,password,role,name,phone_number) "
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT_USER_BY_ID =
                    "SELECT users.id,email,password,active,role,name,phone_number,avatar_url " +
                    "FROM users WHERE users.id=?";
    private static final String SELECT_USER_BY_EMAIL =
                    "SELECT users.id,email,password,active,role,name,phone_number,avatar_url " +
                    "FROM users WHERE email=?";
    private static final String UPDATE_USER_DATA_BY_EMAIL =
                    "UPDATE users " +
                    "SET name=?,phone_number=?,avatar_url=? " +
                    "WHERE email=?";
    private static final String UPDATE_PASSWORD_BY_EMAIL =
                    "UPDATE users SET password=? WHERE email=?";
    private static final String UPDATE_STATUS_BY_EMAIL =
                    "UPDATE users SET active=? WHERE email=?";


    @Override
    public void insert(User object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getUserRole().value);
            statement.setString(4, object.getName());
            statement.setString(5, object.getPhoneNumber());
            statement.executeUpdate();
            logger.info(String.format("Added new user: %s", object.getEmail()));
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to add user: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void update(User object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_USER_DATA_BY_EMAIL);
            statement.setString(1, object.getName());
            statement.setString(2, object.getPhoneNumber());
            statement.setString(3, object.getAvatarURL());
            statement.setString(4, object.getEmail());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update user data by email %s: %s", object.getEmail(), e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_BY_EMAIL);
            statement.setString(1, newPassword);
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to change password by email %s: %s", email, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateStatus(String email, Boolean newStatus) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_BY_EMAIL);
            statement.setBoolean(1, newStatus);
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to change status by email %s: %s", email, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override @Deprecated
    public void delete(User object) {
    }

    @Override @Deprecated
    public List<User> selectAll() throws DaoException {
        return null;
    }

    @Override
    public User selectById(Long key) throws DaoException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, key);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 2;
                String email = resultSet.getString(index++);
                String password = resultSet.getString(index++);
                Boolean status = resultSet.getBoolean(index++);
                UserRole role = UserRole.valueOf(resultSet.getString(index++).toUpperCase());
                String name = resultSet.getString(index++);
                String phoneNumber = resultSet.getString(index++);
                String avatarURL = resultSet.getString(index);
                user = UserFactory.INSTANCE.createFull(key, email, password, role, status, name, phoneNumber, avatarURL);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find user with id %d: %s", key, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return user;
    }

    @Override
    public User selectByEmail(String emailKey) throws DaoException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            statement.setString(1, emailKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                String email = resultSet.getString(index++);
                String password = resultSet.getString(index++);
                boolean status = resultSet.getBoolean(index++);
                UserRole role = UserRole.valueOf(resultSet.getString(index++).toUpperCase());
                String name = resultSet.getString(index++);
                String phoneNumber = resultSet.getString(index++);
                String avatarURL = resultSet.getString(index);
                user = UserFactory.INSTANCE.createFull(id, email, password, role, status, name, phoneNumber, avatarURL);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find user with email %s: %s", emailKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return user;
    }
}
