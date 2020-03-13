package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.impl.MovieEventDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.model.MovieEventSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public Object[][] passEvents() throws ParseException {
        String sDate1="09/02/2020";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        List<Long> movieIds = new ArrayList<>(3);
        movieIds.add(38L);
        movieIds.add(39L);
        movieIds.add(47L);

        MovieEvent event1 = MovieEventSupplier.INSTANCE.createBasic(
                date,
                movieIds,
                0,
                true,
                "Первый мститель");

        String sDate2="23/02/2020";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

        MovieEvent event2 = MovieEventSupplier.INSTANCE.createBasic(
                date,
                movieIds,
                0,
                true,
                "Первый мститель: День защитница отчества EDITION");

        String sDate3="19/02/2020";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);

        List<Long> movieIds3 = new ArrayList<>(3);
        movieIds3.add(48L);
        movieIds3.add(35L);
        movieIds3.add(36L);

        MovieEvent event3 = MovieEventSupplier.INSTANCE.createBasic(
                date,
                movieIds3,
                0,
                true,
                "Tarantino masterpieces");

        return new Object[][] {{event1}, {event2}, {event3}};
    }

    @Test(dataProvider = "event_provider")
    public void insertEventsTest(MovieEvent event) throws DaoException {
        MovieEventDao dao = new MovieEventDaoImpl();
        dao.add(event);
    }

    @Test
    public void selectAllEventsTest() throws DaoException {
        MovieEventDao dao = new MovieEventDaoImpl();
        List<MovieEvent> events = dao.findAll(0, 0);
        for (MovieEvent event1 : events) {
            logger.debug(event1 + "\n");
        }
        Assert.assertEquals(events.size(), 3);
    }

    @Test
    public void deleteAllEventsTest() throws DaoException {
        MovieEventDao dao = new MovieEventDaoImpl();
        dao.deleteAll();

        List<MovieEvent> events = dao.findAll(0, 0);
        Assert.assertEquals(events.size(), 0);
    }
}
