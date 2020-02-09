package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.model.Review;

import java.util.List;

public interface ReviewService {

    void addReview(long userId, long eventId, int rate, String overall, String reviewText) throws ServiceException;

    List<Review> findAllReviews(int offset, int limit) throws ServiceException;

    List<Review> findAllUserReviewsById(long id, int offset, int limit) throws ServiceException;

    Review findById(Long id) throws ServiceException;

    void deleteById(Long id) throws ServiceException;
}
