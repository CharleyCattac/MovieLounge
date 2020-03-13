package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.database.dao.ReviewDao;
import com.lobach.movielounge.database.dao.impl.ReviewDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Review;
import com.lobach.movielounge.model.ReviewSupplier;
import com.lobach.movielounge.service.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    ReviewDao dao;

    public ReviewServiceImpl() {
        dao = new ReviewDaoImpl();
    }

    @Override
    public void addReview(long userId, long eventId, int rate, String overall, String reviewText) throws ServiceException {
        try {
            Review review = ReviewSupplier.INSTANCE.createBasic(userId, eventId, rate, overall, reviewText);
            dao.add(review);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Review> findAllReviews(int offset, int limit) throws ServiceException {
        try {
            return dao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Review> findAllUserReviewsById(long id, int offset, int limit) throws ServiceException {
        try {
            return dao.findByUserId(id, offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Review findById(Long id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            dao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
