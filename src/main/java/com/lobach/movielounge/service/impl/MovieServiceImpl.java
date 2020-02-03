package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.database.dao.MovieDao;
import com.lobach.movielounge.database.dao.impl.MovieDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    private MovieDao dao;

    public MovieServiceImpl() {
        dao = new MovieDaoImpl();
    }

    @Override
    public List<Movie> findAllMovies(int offset, int limit) throws ServiceException {
        try {
            return dao.selectAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Movie findById(Long id) throws ServiceException {
        try {
            return dao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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
