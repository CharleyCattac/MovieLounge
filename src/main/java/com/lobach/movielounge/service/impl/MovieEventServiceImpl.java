package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.model.MovieEventFactory;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.validator.MovieEventValidator;

import java.util.Date;
import java.util.List;

public class MovieEventServiceImpl implements MovieEventService {

    private MovieEventDao eventDao;

    public MovieEventServiceImpl() {
        eventDao = new MovieEventDaoImpl();
    }

    @Override
    public boolean createEvent(Date date, String theme, List<Long> movieIds) throws ServiceException {
        if (!MovieEventValidator.isAtLeastInAWeek(date)) {
            return false;
        }
        try {
            MovieEvent event = MovieEventFactory.INSTANCE
                    .createBasic(date, movieIds, 0,false, theme);
            eventDao.add(event);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<MovieEvent> findAllEvents(int offset, int limit) throws ServiceException {
        if (offset < 0 || limit < 0) {
            throw new ServiceException();
        }
        try {
            return eventDao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public MovieEvent findEventById(long id) throws ServiceException {
        try {
            return eventDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void switchAvailabilityById(long id, boolean currentAvailability) throws ServiceException {
        try {
            eventDao.updateAvailabilityById(id, !currentAvailability);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MovieEvent> findEventByMovieId(long movieId) throws ServiceException {
        try {
            return eventDao.findByMovieId(movieId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean increaseBookingAmountById(long id, int currentAmount, int add) throws ServiceException {
        if (!MovieEventValidator.amountCanBeIncreased(currentAmount, add)) {
            return false;
        }
        try {
            eventDao.updateParticipantAmountById(id, currentAmount + add);
            if (currentAmount + add == MovieEventValidator.getMaxBookingAmount()) {
                eventDao.updateAvailabilityById(id, false);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean decreaseBookingAmountById(long id, int currentAmount, int sub) throws ServiceException {
        if (!MovieEventValidator.amountCanBeIncreased(currentAmount, sub)) {
            return false;
        }
        try {
            if (currentAmount == MovieEventValidator.getMaxBookingAmount()) {
                eventDao.updateAvailabilityById(id, true);
            }
            eventDao.updateParticipantAmountById(id, currentAmount - sub);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public void deleteEventById(long id) throws ServiceException {
        try {
            eventDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
