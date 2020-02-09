package com.lobach.movielounge.database.dao.impl;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.connection.ProxyConnection;
import com.lobach.movielounge.database.dao.MovieDao;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.model.MovieFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {

    private static final String INSERT =
            "INSERT INTO movies (title,description,poster_url,release_year,director,rating) "
                    + "VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL =
            "SELECT movies.id,title,description,poster_url,release_year,director,rating "
                    + "FROM movies ORDER BY title";
    private static final String SELECT_ALL_LIMITED =
            "SELECT movies.id,title,description,poster_url,release_year,director,rating "
                    + "FROM movies ORDER BY title LIMIT ?,?";
    private static final String SELECT_BY_ID =
            "SELECT movies.id,title,description,poster_url,release_year,director,rating "
                    + "FROM movies WHERE movies.id=?";
    private static final String UPDATE_RATING_BY_TITLE =
            "UPDATE movies SET rating=? WHERE title=?";
    private static final String DELETE_BY_ID = "DELETE FROM movies WHERE movies.id=?";
    private static final String DELETE_ALL = "DELETE FROM movies";

    @Override
    public void add(Movie object) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getDescription());
            statement.setString(3, object.getPoster());
            statement.setInt(4, object.getReleaseYear());
            statement.setString(5, object.getDirector());
            statement.setFloat(6, object.getRating());
            statement.executeUpdate();
            logger.info(String.format("Added new movie: %s", object.getTitle()));
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to add movie: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateRating(String title, Float newRating) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_RATING_BY_TITLE);
            statement.setFloat(1, newRating);
            statement.setString(2, title);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to update rating by title %s: %s", title, e));
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Movie findById(Long idKey) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Movie movie = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, idKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                long id = resultSet.getLong(index++);
                String title = resultSet.getString(index++);
                String description = resultSet.getString(index++);
                String posterUrl = resultSet.getString(index++);
                int releaseYear = resultSet.getInt(index++);
                String director = resultSet.getString(index++);
                float rating = resultSet.getFloat(index);
                movie = MovieFactory.INSTANCE
                        .createFull(id, title, description, posterUrl, releaseYear, director, rating);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to find movie with id %d: %s", idKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movie;
    }

    @Override
    public List<Movie> findAll(int offset, int limit) throws DaoException {
        List<Movie> movies = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
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
                String title = resultSet.getString(index++);
                String description = resultSet.getString(index++);
                String posterUrl = resultSet.getString(index++);
                int releaseYear = resultSet.getInt(index++);
                String director = resultSet.getString(index++);
                float rating = resultSet.getFloat(index);
                movies.add(MovieFactory.INSTANCE.createFull(id, title, description, posterUrl, releaseYear, director, rating));
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to create movie list: %s", e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return movies;
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to delete movie %d:", id), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to delete all movies: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
    }
}
