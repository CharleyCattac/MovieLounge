package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.impl.MovieDaoImpl;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.model.MovieEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.List;

public class MovieEventDaoTest {
    private static final Logger logger = LogManager.getLogger();

    @BeforeClass
    public void initConnectionPool() {
        ConnectionPool.INSTANCE.setUpPool();
    }

    @AfterClass
    public void destroyConnectionPool() {
        ConnectionPool.INSTANCE.destroyPool();
    }

    @DataProvider(name = "event_provider")
    public Object[][] passEvents() {
        MovieEvent event1 = new MovieEvent();
        event1.setDate(Date.valueOf("2020-02-13"));
        event1.getMovieIds().add(38L);
        event1.getMovieIds().add(39L);
        event1.getMovieIds().add(47L);

        MovieEvent event2 = new MovieEvent();
        event2.setDate(Date.valueOf("2020-02-16"));
        event2.getMovieIds().add(38L);
        event2.getMovieIds().add(39L);
        event2.getMovieIds().add(47L);

        MovieEvent event3 = new MovieEvent();
        event3.setDate(Date.valueOf("2020-02-19"));
        event3.getMovieIds().add(48L);
        event3.getMovieIds().add(35L);
        event3.getMovieIds().add(36L);

        return new Object[][] {{event1}, {event2}, {event3}};
    }

    @Test(dataProvider = "event_provider")
    public void insertEventsTest(MovieEvent event) throws DaoException {
        MovieEventDao dao = new MovieEventDaoImpl();
        dao.insert(event);
    }

    @Test
    public void selectAllEventsTest() throws DaoException {
        MovieEventDao dao = new MovieEventDaoImpl();
        List<MovieEvent> events = dao.selectAll(0, 0);
        for (MovieEvent event1 : events) {
            logger.debug(event1 + "\n");
        }
        Assert.assertEquals(events.size(), 3);
    }
}
