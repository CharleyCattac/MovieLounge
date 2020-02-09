package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieEvent;

import java.sql.Date;
import java.util.List;

public interface MovieEventService {

    List<MovieEvent> findAllEvents(int offset, int limit) throws ServiceException;

    MovieEvent findEventById(long id) throws ServiceException;

    void switchAvailabilityById(long id, boolean currentAvailability) throws ServiceException;

    void incrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void decrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void deleteSessionById(long id) throws ServiceException;

    List<MovieEvent> findSessionsByMovieId(long movieId) throws ServiceException;
}
