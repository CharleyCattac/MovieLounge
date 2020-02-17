package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieEvent;

import java.util.Date;
import java.util.List;

public interface MovieEventService {

    void createEvent (Date date, String theme, List<Long> movieIds) throws ServiceException;

    List<MovieEvent> findAllEvents(int offset, int limit) throws ServiceException;

    MovieEvent findEventById(long id) throws ServiceException;

    List<MovieEvent> findEventByMovieId(long movieId) throws ServiceException;

    void switchAvailabilityById(long id, boolean currentAvailability) throws ServiceException;

    void increaseBookingAmountById(long id, int currentAmount, int add) throws ServiceException;

    void decreaseBookingAmountById(long id, int currentAmount, int sub) throws ServiceException;

    void deleteEventById(long id) throws ServiceException;
}
