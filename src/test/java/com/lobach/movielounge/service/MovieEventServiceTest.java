package com.lobach.movielounge.service;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.MovieEventDao;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class MovieEventServiceTest {
    private static final Logger logger = LogManager.getLogger();

    @BeforeClass
    public void initConnectionPool() {
        ConnectionPool.INSTANCE.setUpPool();
    }

    @AfterClass
    public void destroyConnectionPool() {
        ConnectionPool.INSTANCE.destroyPool();
    }

    @Test
    public void selectAllEventsTest() throws ServiceException {
        MovieEventService eventService = new MovieEventServiceImpl();
        MovieService movieService = new MovieServiceImpl();

        List<MovieEvent> events = eventService.findAllEvents(0,0);
        for (MovieEvent event1 : events) {
            for (Long movieId : event1.getMovieIds()) {
                event1.getMovies().add(movieService.findById(movieId));
            }
            logger.debug(event1 + "\n");
        }
        Assert.assertEquals(events.size(), 3);
    }
}
