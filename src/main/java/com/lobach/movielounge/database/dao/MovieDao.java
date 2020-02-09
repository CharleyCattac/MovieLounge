package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Movie;

public interface MovieDao extends BaseDao<Movie> {

    void updateRating(String title, Float newRating) throws DaoException;
}
