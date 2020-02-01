package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.impl.MovieDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class MovieDaoTest {
    private static final Logger logger = LogManager.getLogger();

    @BeforeClass
    public void initConnectionPool() {
        ConnectionPool.INSTANCE.setUpPool();
    }

    @AfterClass
    public void destroyConnectionPool() {
        //ConnectionManager.INSTANCE.destroyPool();
    }

    @DataProvider(name = "movie_provider")
    public Object[][] passMovies() {
        Movie testMovie1 = new Movie();
        testMovie1.setTitle("Avengers");
        testMovie1.setDescription("First film in legendary avenger saga");
        testMovie1.setReleaseYear(2012);
        testMovie1.setDirector("Anthony Russo");
        testMovie1.setRating(8.5F);

        Movie testMovie2 = new Movie();
        testMovie2.setTitle("Kill Bill: Volume 1");
        testMovie2.setDescription("After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.");
        testMovie2.setReleaseYear(2003);
        testMovie2.setDirector("Quentin Tarantino");
        testMovie2.setRating(8.1F);

        Movie testMovie3 = new Movie();
        testMovie3.setTitle("Kill Bill: Volume 2");
        testMovie3.setDescription("The Bride continues her quest of vengeance against her former boss and lover Bill, the reclusive bouncer Budd, and the treacherous, one-eyed Elle.");
        testMovie3.setReleaseYear(2004);
        testMovie3.setDirector("Quentin Tarantino");
        testMovie3.setRating(8.0F);

        Movie testMovie4 = new Movie();
        testMovie4.setTitle("Назад в Будущее");
        testMovie4.setDescription("Подросток Марти с помощью машины времени, сооружённой его другом-профессором доком Брауном, попадает из 80-х в далекие 50-е. Там он встречается со своими будущими родителями, ещё подростками, и другом-профессором, совсем молодым.");
        testMovie4.setReleaseYear(2013);
        testMovie4.setDirector("Роберт Земекис");
        testMovie4.setRating(8.626F);

        return new Object[][] {{testMovie1}, {testMovie2}, {testMovie3}, {testMovie4}};
    }

    @Test(dataProvider = "movie_provider")
    public void insertMoviesTest(Movie testMovie) throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        dao.insert(testMovie);
    }

    @DataProvider(name = "title_provider")
    public Object[][] passTitle() {
        return new Object[][] {
                {"Назад в Будущее", 8.626F},
                {"Kill Bill: Volume 1", 8.1F},
                {"Kill Bill: Volume 2", 8.0F}
        };
    }

    @Test (dataProvider = "title_provider")
    public void getMoviesByTitleTest(String title, Float rating) throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        Movie movie = dao.selectByTitle(title);
        logger.info(String.format("Movie found: %s", movie));
        Assert.assertEquals(movie.getRating(), rating);
    }

    @Test
    public void getAllMoviesTest() throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        List<Movie> movies = dao.selectAll();
        Assert.assertEquals(movies.size(), 4);
    }

    @DataProvider(name = "rating_provider")
    public Object[][] passRating() {
        return new Object[][] {
                {"Назад в Будущее", 8.7F},
                {"Kill Bill: Volume 1", 9F},
                {"Kill Bill: Volume 2", 7F}
        };
    }

    @Test (dataProvider = "rating_provider")
    public void updateRatingTest(String title, Float newRating) throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        dao.updateRating(title, newRating);

        Movie movie = dao.selectByTitle(title);
        logger.info(String.format("Movie found: %s", movie));
        Assert.assertEquals(movie.getRating(), newRating);
    }

    @DataProvider(name = "remove_title_provider")
    public Object[][] passTitleToRemove() {
        return new Object[][] {
                {"Назад в Будущее", 3},
                {"Kill Bill: Volume 1", 2},
                {"Kill Bill: Volume 2", 1}
        };
    }

    @Test (dataProvider = "remove_title_provider")
    public void removeByTitleTest(String title, int newAmount) throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        dao.deleteByTitle(title);

        List<Movie> movies = dao.selectAll();
        Assert.assertEquals(movies.size(), newAmount);
    }

    @Test
    public void removeAllTest() throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        dao.deleteAll();

        List<Movie> movies = dao.selectAll();
        Assert.assertEquals(movies.size(), 0);
    }
}
