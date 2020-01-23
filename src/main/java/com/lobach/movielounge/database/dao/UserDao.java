package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionManager;
import com.lobach.movielounge.exception.DatabaseException;
import com.lobach.movielounge.model.entity.Role;
import com.lobach.movielounge.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public enum UserDao implements BaseDao<User> {
    INSTANCE;

    private static final String INSERT_USER =
                    "INSERT INTO users (email,password,role,name,phone_number) "
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT_USER_BY_ID =
                    "SELECT users.id,email,password,role,name,phone_number,age,sex,avatar_url " +
                    "FROM users WHERE users.id=?";
    private static final String SELECT_USER_BY_EMAIL =
                    "SELECT users.id,email,password,role,name,phone_number,age,sex,avatar_url " +
                    "FROM users WHERE email=?";
    private static final String UPDATE_USER_DATA_BY_EMAIL =
                    "UPDATE users " +
                    "SET name=?,phone_number=?,age=?,sex=?,avatar_url=? " +
                    "WHERE email=?";
    private static final String UPDATE_PASSWORD_BY_EMAIL =
                    "UPDATE users SET password=? WHERE email=?";


    @Override
    public void add(User object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getRole().value);
            statement.setString(4, object.getName());
            statement.setString(5, object.getPhoneNumber());
            statement.executeUpdate();
            logger.info(String.format("Added new user: %s", object.getEmail()));
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to add user: %s", e));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void update(User object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_USER_DATA_BY_EMAIL);
            statement.setString(1, object.getName());
            statement.setString(2, object.getPhoneNumber());
            statement.setInt(3, object.getAge());
            statement.setString(4, object.getSex());
            statement.setString(5, object.getAvatarURL());
            statement.setString(6, object.getEmail());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to update user data by email %s: %s", object.getEmail(), e));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    public void updatePassword(String email, String newPassword) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_BY_EMAIL);
            statement.setString(1, newPassword);
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to change password by email %s: %s", email, e));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    @Override @Deprecated
    public void remove(User object) {
    }

    @Override @Deprecated
    public List<User> getAll() throws DatabaseException {
        return null;
    }

    public User getById(Long key) throws DatabaseException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, key);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(key);
                int index = 2;
                String email = resultSet.getString(index++);
                user.setEmail(email);
                String password = resultSet.getString(index++);
                user.setPassword(password);
                String role = resultSet.getString(index++);
                user.setRole(Role.valueOf(role.toUpperCase()));
                String name = resultSet.getString(index++);
                user.setName(name);
                String phoneNumber = resultSet.getString(index++);
                user.setPhoneNumber(phoneNumber);
                Integer age = resultSet.getInt(index++);
                user.setAge(age);
                String sex = resultSet.getString(index++);
                user.setSex(sex);
                String avatarURL = resultSet.getString(index);
                user.setAvatarURL(avatarURL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to find user with id %d: %s", key, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
        return user;
    }

    public User getByEmail(String emailKey) throws DatabaseException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            statement.setString(1, emailKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                user.setId(id);
                String email = resultSet.getString(index++);
                user.setEmail(email);
                String password = resultSet.getString(index++);
                user.setPassword(password);
                String role = resultSet.getString(index++);
                user.setRole(Role.valueOf(role.toUpperCase()));
                String name = resultSet.getString(index++);
                user.setName(name);
                String phoneNumber = resultSet.getString(index++);
                user.setPhoneNumber(phoneNumber);
                Integer age = resultSet.getInt(index++);
                user.setAge(age);
                String sex = resultSet.getString(index++);
                user.setSex(sex);
                String avatarURL = resultSet.getString(index);
                user.setAvatarURL(avatarURL);
            }
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to find user with email %s: %s", emailKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
        return user;
    }
}
