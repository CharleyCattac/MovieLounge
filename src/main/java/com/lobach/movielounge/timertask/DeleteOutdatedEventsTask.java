package com.lobach.movielounge.timertask;


import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.validator.MovieEventValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.TimerTask;

class DeleteOutdatedEventsTask extends TimerTask {
    private static DeleteOutdatedEventsTask INSTANCE;
    private static final Logger logger = LogManager.getLogger();
    private MovieEventDao eventDao;

    private DeleteOutdatedEventsTask() {
        eventDao = new MovieEventDaoImpl();
    }

    static DeleteOutdatedEventsTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeleteOutdatedEventsTask();
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        try {
            List<MovieEvent> eventList = eventDao.findAll(0,0);
            for (MovieEvent event : eventList) {
                if (MovieEventValidator.isOutdated(event)) {
                    eventDao.deleteById(event.getId());
                }
            }
        } catch (DaoException e) {
            logger.error("Failed to set outdated events timer task ", e);
        }
    }
}
