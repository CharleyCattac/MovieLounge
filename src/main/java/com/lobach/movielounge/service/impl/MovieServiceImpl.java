package com.lobach.movielounge.service.impl;

import com.lobach.movielounge.model.MovieSupplier;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.database.dao.MovieDao;
import com.lobach.movielounge.database.dao.impl.MovieDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private MovieDao dao;

    public MovieServiceImpl() {
        dao = new MovieDaoImpl();
    }

    @Override
    public void addMovie(String title, String description, String posterUrl,
                         int releaseYear, String director, float rating) throws ServiceException {
        try {
            Movie movie = MovieSupplier
                    .INSTANCE.createBasic(title, description, posterUrl, releaseYear, director, rating);
            dao.add(movie);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Movie> findAllMovies(int offset, int limit) throws ServiceException {
        try {
            return dao.findAll(offset, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Movie findById(Long id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long findIdByTitle(String title) throws ServiceException {
        try {
            return dao.findIdByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            dao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
