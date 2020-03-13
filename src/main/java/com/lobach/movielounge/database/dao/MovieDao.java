package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Movie;
/**
 * Dao interface for {@code Movie} type.
 *
 * @author Renata Lobach
 */
public interface MovieDao extends BaseDao<Movie> {
    /**
     * This method looks for a movie id
     * (also it checks if booking really exists);
     * @param title is the title of the movie;
     * @return id if movie with such title exists, 0 if does not;
     * @throws DaoException is SQLException  has been caught.
     */
    Long findIdByTitle(String title) throws DaoException;
}
