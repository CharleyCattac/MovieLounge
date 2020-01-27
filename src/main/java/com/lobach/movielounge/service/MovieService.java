package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAllMovies() throws ServiceException;

    List<Movie> findByIdList(List<Long> ids) throws ServiceException;

    Movie findByTitle(String title) throws ServiceException;

    void deleteByTitle(String title) throws ServiceException;
}
