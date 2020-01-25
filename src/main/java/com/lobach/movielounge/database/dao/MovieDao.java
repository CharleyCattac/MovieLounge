package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.entity.Movie;

public interface MovieDao extends BaseDao<Movie> {
    void updateRating(String title, Float newRating) throws DaoException;
    void deleteByTitle(String title) throws DaoException;
    void deleteAll() throws DaoException;
    Movie selectByTitle(String titleKey) throws DaoException;
}
