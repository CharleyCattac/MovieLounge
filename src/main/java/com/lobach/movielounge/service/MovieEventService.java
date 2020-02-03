package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieEvent;

import java.sql.Date;
import java.util.List;

public interface MovieEventService {

    List<MovieEvent> findAllEvents(int offset, int limit) throws ServiceException;

    List<MovieEvent> findSessionsByMovieId(long movieId) throws ServiceException;

    List<MovieEvent> findSessionsByMovieTitle(List<MovieEvent> initialList, String title);

    MovieEvent findSessionByDate(Date date) throws ServiceException;

    void incrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void decrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void deleteSessionById(long id) throws ServiceException;
}
