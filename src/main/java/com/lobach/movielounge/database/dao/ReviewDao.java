package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Review;

import java.util.List;

public interface ReviewDao extends BaseDao<Review> {

    List<Review> findByUserId(long id, int offset, int limit) throws DaoException;
}
