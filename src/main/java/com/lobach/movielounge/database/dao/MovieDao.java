package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionManager;
import com.lobach.movielounge.exception.DatabaseException;
import com.lobach.movielounge.model.entity.Movie;
import com.lobach.movielounge.model.entity.Role;
import com.lobach.movielounge.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum MovieDao implements BaseDao<Movie>{
    INSTANCE;

    private static final String INSERT_MOVIE =
                    "INSERT INTO movies (title,description,release_year,director,rating) "
                    + "VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_MOVIES =
                    "SELECT movies.id,title,description,release_year,director,rating " +
                    "FROM movies";
    private static final String SELECT_MOVIE_BY_TITLE =
                    "SELECT movies.id,title,description,release_year,director,rating " +
                    "FROM movies WHERE title=?";
    private static final String UPDATE_RATING_BY_TITLE =
                    "UPDATE movies SET rating=? WHERE title=?";
    private static final String DELETE_BY_TITLE = "DELETE FROM movies WHERE title=?";
    private static final String DELETE_ALL = "DELETE FROM movies";

    @Override
    public void add(Movie object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_MOVIE);
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getReleaseYear());
            statement.setString(4, object.getDirector());
            statement.setFloat(5, object.getRating());
            statement.executeUpdate();
            logger.info(String.format("Added new movie: %s", object.getTitle()));
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to add movie: %s", e.getMessage()));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void update(Movie object) throws DatabaseException {
    }

    public void updateRating(String title, Float newRating) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_RATING_BY_TITLE);
            statement.setFloat(1, newRating);
            statement.setString(2, title);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to update rating by title %s: %s", title, e.getMessage()));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void remove(Movie object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(DELETE_BY_TITLE);
            statement.setString(1, object.getTitle());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to delete movie %s: %s", object.getTitle(), e.getMessage()));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    public void removeAll() throws DatabaseException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.createStatement();
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to delete all movies: %s", e.getMessage()));
        } finally {
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public List<Movie> getAll() throws DatabaseException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_MOVIES);
            while (resultSet.next()) {
                Movie movie = new Movie();
                int index = 1;
                Long id = resultSet.getLong(index++);
                movie.setId(id);
                String title = resultSet.getString(index++);
                movie.setTitle(title);
                String description = resultSet.getString(index++);
                movie.setDescription(description);
                Integer releaseYear = resultSet.getInt(index++);
                movie.setReleaseYear(releaseYear);
                String director = resultSet.getString(index++);
                movie.setDirector(director);
                Float rating = resultSet.getFloat(index);
                movie.setRating(rating);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to create movie list: %s", e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
        return movies;
    }


    public Movie getByTitle(String titleKey) throws DatabaseException {
        Movie movie = new Movie();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_MOVIE_BY_TITLE);
            statement.setString(1, titleKey);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                movie.setId(id);
                index++;
                movie.setTitle(titleKey);
                String description = resultSet.getString(index++);
                movie.setDescription(description);
                Integer releaseYear = resultSet.getInt(index++);
                movie.setReleaseYear(releaseYear);
                String director = resultSet.getString(index++);
                movie.setDirector(director);
                Float rating = resultSet.getFloat(index);
                movie.setRating(rating);
            }
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed to find movie with title %s: %s", titleKey, e));
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
            ConnectionManager.INSTANCE.releaseConnection(connection);
        }
        return movie;
    }
}
