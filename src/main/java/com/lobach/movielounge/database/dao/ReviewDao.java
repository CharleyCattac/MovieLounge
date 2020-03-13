package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Review;

import java.util.List;
/**
 * Dao interface for {@code Review} type.
 *
 * @author Renata Lobach
 */
public interface ReviewDao extends BaseDao<Review> {
    /**
     * This method looks for a list of reviews made by certain user;
     * @param id is the id of the user;
     * @param offset is the object number to start selecting;
     * @param limit is the maximum amount of objects necessary;;
     * @return list of reviews (empty list if nothing found);
     * @throws DaoException is SQLException  has been caught.
     */
    List<Review> findByUserId(long id, int offset, int limit) throws DaoException;
}
