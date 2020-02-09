package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.validator.MovieSessionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MovieEventServiceImpl implements MovieEventService {

    private MovieEventDao dao;

    public MovieEventServiceImpl() {
        dao = new MovieEventDaoImpl();
    }

    @Override
    public List<MovieEvent> findAllEvents(int offset, int limit) throws ServiceException {
        try {
            return dao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public MovieEvent findEventById(long id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void switchAvailabilityById(long id, boolean currentAvailability) throws ServiceException {
        try {
            dao.updateAvailabilityById(id, !currentAvailability);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MovieEvent> findSessionsByMovieId(long movieId) throws ServiceException {
        try {
            return dao.findByMovieId(movieId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void incrementBookingAmountById(long id, int currentAmount) throws ServiceException {
        try {
            dao.updateParticipantAmountById(id, ++currentAmount);
            if (currentAmount >= MovieSessionValidator.getMaxBookingAmount()) {
                dao.updateAvailabilityById(id, false);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void decrementBookingAmountById(long id, int currentAmount) throws ServiceException {
        try {
            dao.updateParticipantAmountById(id, --currentAmount);
            if (currentAmount == MovieSessionValidator.getMaxBookingAmount() - 1) {
                dao.updateAvailabilityById(id, true);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteSessionById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
