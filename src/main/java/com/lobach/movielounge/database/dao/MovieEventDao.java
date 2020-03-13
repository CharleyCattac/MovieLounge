package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;

import java.sql.Date;
import java.util.List;
/**
 * Dao interface for {@code MovieEvent} type.
 *
 * @author Renata Lobach
 */
public interface MovieEventDao extends BaseDao<MovieEvent> {
    /**
     * This method looks for a list of event that have the movie with given id;
     * @param movieId is the id of the movie;
     * @return list of events (empty list if nothing found);
     * @throws DaoException is SQLException  has been caught.
     */
    List<MovieEvent> findByMovieId(long movieId) throws DaoException;

    /**
     * This method updates status of event (if it's available for booking of not);
     * @param id is id of event;
     * @param status is the new status to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateAvailabilityById(Long id, boolean status) throws DaoException;

    /**
     * This method updates whole amount of bookings made for this event;
     * @param id is id of event;
     * @param newAmount is the new amount to be set;
     * @throws DaoException is SQLException  has been caught.
     */
    void updateParticipantAmountById(Long id, int newAmount) throws DaoException;
}
