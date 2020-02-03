package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;

import java.sql.Date;
import java.util.List;

public interface MovieEventDao extends BaseDao<MovieEvent> {

    MovieEvent selectByDate(Date date) throws DaoException;

    List<MovieEvent> selectByMovieId(long movieId) throws DaoException;

    void updateAvailabilityById(Long id, boolean status) throws DaoException;

    void updateParticipantAmountById(Long id, int newAmount) throws DaoException;

    void deleteById(Long id) throws DaoException;
}
