package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.entity.MovieSession;

import java.sql.Date;
import java.util.List;

public interface MovieSessionDao extends BaseDao<MovieSession> {
    MovieSession getByDate(Date date) throws DaoException;
    List<MovieSession> getByMovieId(long movieId) throws DaoException;
    void updateAvailabilityByDate(Date date, boolean status) throws DaoException;
    void updateParticipantAmountByDate(Date date, int newAmount) throws DaoException;
    //void updateDate(Date date);
    //void updateMovieIdByDate(int movieNum, Long movieId);
    void deleteByDate(Date date) throws DaoException;
}
