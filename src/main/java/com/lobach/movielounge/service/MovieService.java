package com.lobach.movielounge.service;

import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Movie;

import java.util.List;

public interface MovieService {

    void addMovie(String title, String description, String posterUrl,
                  int releaseYear, String director, float rating) throws ServiceException;

    List<Movie> findAllMovies(int offset, int limit) throws ServiceException;

    Movie findById(Long id) throws ServiceException;

    Long findIdByTitle(String title) throws ServiceException;

    void deleteById(long id) throws ServiceException;
}
