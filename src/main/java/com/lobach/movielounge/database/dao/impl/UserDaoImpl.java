package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.dao.UserDao;
import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.UserSupplier;
import com.lobach.movielounge.model.UserStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER =
            "INSERT INTO users (email,password,role,name,phone_number,avatar_url) "
                    + "VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL =
            "SELECT users.id,email,password,status,role,name,phone_number,avatar_url " +
                    "FROM users";
    private static final String SELECT_ALL_LIMITED =
            "SELECT users.id,email,password,status,role,name,phone_number,avatar_url " +
                    "FROM users LIMIT ?,?";
    private static final String SELECT_USER_BY_ID =
            "SELECT users.id,email,password,status,role,name,phone_number,avatar_url " +
                    "FROM users WHERE users.id=?";
    private static final String SELECT_USER_BY_EMAIL_PASSWORD =
            "SELECT users.id,role,status,name,avatar_url,phone_number FROM users WHERE email=? AND password=?";
    private static final String UPDATE_USER_DATA_BY_ID =
            "UPDATE users SET email=?,name=?,phone_number=?,avatar_url=? " +
                    "WHERE users.id=?";
    private static final String UPDATE_PASSWORD_BY_ID =
            "UPDATE users SET password=? WHERE users.id=?";
    private static final String UPDATE_STATUS_BY_ID =
            "UPDATE users SET status=? WHERE users.id=?";
    private static final String UPDATE_ROLE_BY_ID =
            "UPDATE users SET role=? WHERE users.id=?";


    @Override
    public void add(User object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getUserRole().value);
            statement.setString(4, object.getName());
            statement.setString(5, object.getPhoneNumber());
            statement.setString(6, object.getAvatarURL());
            statement.executeUpdate();
            logger.info(String.format("Added new user: %s", object.getEmail()));
        } catch (SQLException e) {
            throw new DaoException("Failed to add user", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateById(long id, String newEmail, String newName,
                           String newPhoneNumber, String newAvatarUrl)
                throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(UPDATE_USER_DATA_BY_ID);
            statement.setString(1, newEmail);
            statement.setString(2, newName);
            statement.setString(3, newPhoneNumber);
            statement.setString(4, newAvatarUrl);
            statement.setLong(5, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update user data by id %d", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updatePassword(long id, String newPassword) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_BY_ID);
            statement.setString(1, newPassword);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to change password by id %d", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateStatus(long id, String newStatus) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ID);
            statement.setString(1, newStatus);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update status by id %d: ", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateRole(long id, String newRole) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(UPDATE_ROLE_BY_ID);
            statement.setString(1, newRole);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update role by id %d: ", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<User> findAll(int offset, int limit) throws DaoException {
        List<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            if (limit != 0) {
                statement = connection.prepareStatement(SELECT_ALL_LIMITED);
                statement.setInt(1, offset);
                statement.setInt(2, limit);
            } else {
                statement = connection.prepareStatement(SELECT_ALL);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                String email = resultSet.getString(index++);
                String password = resultSet.getString(index++);
                UserStatus status = UserStatus.valueOf(resultSet.getString(index++).toUpperCase());
                UserRole role = UserRole.valueOf(resultSet.getString(index++).toUpperCase());
                String name = resultSet.getString(index++);
                String phoneNumber = resultSet.getString(index++);
                String avatarURL = resultSet.getString(index);
                User user = UserSupplier.INSTANCE.createFull(id, email, password, role, status, name, phoneNumber, avatarURL);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find users: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override @Deprecated
    public void deleteById(Long id) throws DaoException {

    }

    @Override @Deprecated
    public void deleteAll() throws DaoException {
    }

    @Override
    public User findById(Long id) throws DaoException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 2;
                String email = resultSet.getString(index++);
                String password = resultSet.getString(index++);
                UserStatus status = UserStatus.valueOf(resultSet.getString(index++).toUpperCase());
                UserRole role = UserRole.valueOf(resultSet.getString(index++).toUpperCase());
                String name = resultSet.getString(index++);
                String phoneNumber = resultSet.getString(index++);
                String avatarURL = resultSet.getString(index);
                user = UserSupplier.INSTANCE.createFull(id, email, password, role, status, name, phoneNumber, avatarURL);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find user by id %d: ", id), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return user;
    }

    @Override
    public User findByEmailPassword(String emailKey, String passwordKey) throws DaoException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.occupyConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASSWORD);
            statement.setString(1, emailKey);
            statement.setString(2, passwordKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                String roleString = resultSet.getString(index++);
                UserRole role = UserRole.valueOf(roleString.toUpperCase());
                String statusString = resultSet.getString(index++);
                UserStatus status = UserStatus.valueOf(statusString.toUpperCase());
                String name = resultSet.getString(index++);
                String avatarURL = resultSet.getString(index++);
                String phoneNumber = resultSet.getString(index);
                user = UserSupplier.INSTANCE.createReduced(id, role, status, name, phoneNumber, avatarURL);
            }
        } catch (SQLException e) {
            throw new DaoException(
                            String.format("Failed to find user with these email %s and password %s: ",
                            emailKey, passwordKey), e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return user;
    }
}
