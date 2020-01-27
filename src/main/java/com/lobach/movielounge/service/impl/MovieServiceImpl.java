package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.database.dao.MovieDao;
import com.lobach.movielounge.database.dao.impl.MovieDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public enum MovieServiceImpl implements MovieService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private MovieDao dao;

    MovieServiceImpl() {
        dao = new MovieDaoImpl();
    }

    @Override
    public List<Movie> findAllMovies() throws ServiceException {
        try {
            return dao.selectAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Movie> findByIdList(List<Long> ids) throws ServiceException {
        List<Movie> movies = new ArrayList<>();
        try {
            for (Long id : ids) {
                movies.add(dao.selectById(id));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return movies;
    }

    @Override
    public Movie findByTitle(String title) throws ServiceException {
        try {
            return dao.selectByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByTitle(String title) throws ServiceException {
        try {
            dao.deleteByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
