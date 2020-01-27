package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieSession;

import java.sql.Date;
import java.util.List;

public interface MovieSessionDao extends BaseDao<MovieSession> {

    MovieSession getByDate(Date date) throws DaoException;

    List<MovieSession> getByMovieId(long movieId) throws DaoException;

    void updateAvailabilityById(Long id, boolean status) throws DaoException;

    void updateParticipantAmountById(Long id, int newAmount) throws DaoException;

    void deleteById(Long id) throws DaoException;
}
