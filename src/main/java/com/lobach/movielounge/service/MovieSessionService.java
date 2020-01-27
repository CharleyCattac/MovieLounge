package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieSession;

import java.sql.Date;
import java.util.List;

public interface MovieSessionService {

    List<MovieSession> findAllSessions() throws ServiceException;

    List<MovieSession> findSessionsByMovieId(long movieId) throws ServiceException;

    List<MovieSession> findSessionsByMovieTitle(List<MovieSession> initialList, String title);

    MovieSession findSessionByDate(Date date) throws ServiceException;

    void incrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void decrementBookingAmountById(long id, int currentAmount) throws ServiceException;

    void deleteSessionById(long id) throws ServiceException;
}
