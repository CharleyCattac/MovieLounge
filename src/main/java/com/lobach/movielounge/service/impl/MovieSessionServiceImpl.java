package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.service.MovieSessionService;
import com.lobach.movielounge.database.dao.MovieSessionDao;
import com.lobach.movielounge.database.dao.impl.MovieSessionDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.model.MovieSession;
import com.lobach.movielounge.validator.MovieSessionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public enum MovieSessionServiceImpl implements MovieSessionService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private MovieSessionDao dao;

    MovieSessionServiceImpl() {
        dao = new MovieSessionDaoImpl();
    }

    @Override
    public List<MovieSession> findAllSessions() throws ServiceException {
        try {
            return dao.selectAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MovieSession> findSessionsByMovieId(long movieId) throws ServiceException {
        try {
            return dao.getByMovieId(movieId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MovieSession> findSessionsByMovieTitle(List<MovieSession> initialList,
                                                       String title) {
        List<MovieSession> movieSessions = new ArrayList<>();
        for (MovieSession session : initialList) {
            for (Movie movie : session.getMovies()) {
                if (movie.getTitle().equals(title)) {
                    movieSessions.add(session);
                    break;
                }
            }
        }
        return movieSessions;
    }

    @Override
    public MovieSession findSessionByDate(Date date) throws ServiceException {
        try {
            return dao.getByDate(date);
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
