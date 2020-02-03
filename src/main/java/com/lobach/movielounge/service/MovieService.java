package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAllMovies(int offset, int limit) throws ServiceException;

    Movie findById(Long id) throws ServiceException;

    Movie findByTitle(String title) throws ServiceException;

    void deleteByTitle(String title) throws ServiceException;
}
