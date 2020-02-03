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
        ConnectionPool.INSTANCE.destroyPool();
    }

    @DataProvider(name = "movie_provider")
    public Object[][] passMovies() {
        Movie testMovie1 = new Movie();
        testMovie1.setTitle("The Avengers");
        testMovie1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team "
                + "if they are going to stop the mischievous Loki and his alien army from enslaving humanity. ");
        testMovie1.setPoster("https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SY1000_CR0,0,675,1000_AL_.jpg");
        testMovie1.setReleaseYear(2012);
        testMovie1.setDirector("Anthony Russo");
        testMovie1.setRating(8.0F);

        Movie testMovie2 = new Movie();
        testMovie2.setTitle("Kill Bill: Volume 1");
        testMovie2.setDescription("After awakening from a four-year coma, a former assassin wreaks vengeance "
                + "on the team of assassins who betrayed her.");
        testMovie2.setPoster("https://m.media-amazon.com/images/M/MV5BNzM3NDFhYTAtYmU5Mi00NGRmLTljYjgtMDkyODQ4MjNkMGY2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
        testMovie2.setReleaseYear(2003);
        testMovie2.setDirector("Quentin Tarantino");
        testMovie2.setRating(8.1F);

        Movie testMovie3 = new Movie();
        testMovie3.setTitle("Kill Bill: Volume 2");
        testMovie3.setDescription("The Bride continues her quest of vengeance against her former boss and lover Bill, "
                + "the reclusive bouncer Budd, and the treacherous, one-eyed Elle.");
        testMovie3.setPoster("https://m.media-amazon.com/images/M/MV5BNmFiYmJmN2QtNWQwMi00MzliLThiOWMtZjQxNGRhZTQ1MjgyXkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1_.jpg");
        testMovie3.setReleaseYear(2004);
        testMovie3.setDirector("Quentin Tarantino");
        testMovie3.setRating(8.0F);

        Movie testMovie4 = new Movie();
        testMovie4.setTitle("Назад в Будущее");
        testMovie4.setDescription("Подросток Марти с помощью машины времени, сооружённой его другом-профессором "
                + "доком Брауном, попадает из 80-х в далекие 50-е. Там он встречается со своими будущими родителями, "
                + "ещё подростками, и другом-профессором, совсем молодым.");
        testMovie4.setPoster("https://m.media-amazon.com/images/M/MV5BZmU0M2Y1OGUtZjIxNi00ZjBkLTg1MjgtOWIyNThiZWIwYjRiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SY1000_CR0,0,643,1000_AL_.jpg");
        testMovie4.setReleaseYear(2013);
        testMovie4.setDirector("Роберт Земекис");
        testMovie4.setRating(8.626F);

        Movie testMovie5 = new Movie();
        testMovie5.setTitle("Первый мститель");
        testMovie5.setDescription("Стив Роджерс добровольно соглашается принять участие в эксперименте, "
                + "который превратит его в суперсолдата, известного как Первый мститель. Роджерс вступает в "
                + "вооруженные силы США вместе с Баки Барнсом и Пегги Картер, чтобы бороться с враждебной "
                + "организацией ГИДРА, которой управляет безжалостный Красный Череп.");
        testMovie5.setPoster("https://m.media-amazon.com/images/M/MV5BMTYzOTc2NzU3N15BMl5BanBnXkFtZTcwNjY3MDE3NQ@@._V1_SY1000_CR0,0,640,1000_AL_.jpg");
        testMovie5.setReleaseYear(2011);
        testMovie5.setDirector("Джо Джонстон");
        testMovie5.setRating(6.9F);

        Movie testMovie6 = new Movie();
        testMovie6.setTitle("Первый мститель: Другая война");
        testMovie6.setDescription("После беспрецедентных событий, впервые собравших вместе команду Мстителей, "
                + "Стив Роджерс, известный также как Капитан Америка, оседает в Вашингтоне и пытается "
                + "приспособиться к жизни в современном мире. Но покой этому герою только снится — пытаясь помочь "
                + "коллеге из агентства Щ. И. Т., Стив оказывается в центре событий, грозящих катастрофой "
                + "мирового масштаба. Для того, чтобы разоблачить злодейский заговор, Капитан Америка объединяется "
                + "с Черной вдовой. К ним также присоединяется новый соратник, известный как Сокол, однако никто из "
                + "них даже не подозревает, на что способен новый враг.");
        testMovie6.setPoster("https://m.media-amazon.com/images/M/MV5BMzA2NDkwODAwM15BMl5BanBnXkFtZTgwODk5MTgzMTE@._V1_SY1000_CR0,0,685,1000_AL_.jpg");
        testMovie6.setReleaseYear(2014);
        testMovie6.setDirector("Энтони Руссо, Джо Руссо");
        testMovie6.setRating(7.7F);

        Movie testMovie7 = new Movie();
        testMovie7.setTitle("Первый мститель: Противостояние");
        testMovie7.setDescription("Мстители под руководством Капитана Америки оказываются участниками "
                + "разрушительного инцидента, имеющего международный масштаб. Эти события заставляют правительство "
                + "задуматься над тем, чтобы начать регулировать действия всех людей с особыми способностями, "
                + "введя «Акт о регистрации супергероев», вынуждая их подчиняться ООН." +
                "\n" +
                "Видя в этом договоре нарушение гражданских прав и плевок на всё, во что он верит, Стив Роджерс "
                + "открыто восстаёт против нового режима, ведя за собой группу героев, поддерживающих его мнение. "
                + "В свою очередь, Тони Старк видит смысл в новом договоре и становится во главе героев, "
                + "поддерживающих его. Всё это приводит к неизбежному расколу Мстителей и войне между сторонами. "
                + "Сражаясь друг с другом, Старк и Роджерс не знают о том, что таинственный Барон Земо уже строит "
                + "свои собственные планы, воспользовавшись расколом величайших героев Земли.");
        testMovie7.setPoster("https://st.kp.yandex.net/images/film_big/822708.jpg");
        testMovie7.setReleaseYear(2016);
        testMovie7.setDirector("Энтони Руссо, Джо Руссо");
        testMovie7.setRating(7.405F);

        Movie testMovie8 = new Movie();
        testMovie8.setTitle("Таемнае жыццё Уолтэра Міці");
        testMovie8.setDescription("Хто сказаў, што ў сэрцы маленькага чалавека не могуць жыць вялікія мары? "
                + "Нават сціплым і неўзаметку служачаму хочацца часам здзейсніць хай вар'яцкія, але геройскія "
                + "ўчынкі, паверыць у сваю сілу і мужнасць. Чаму ўсіх ратаваць павінны выключна супермэны? ");
        testMovie8.setPoster("https://img.afisha.tut.by/static/media/340x0s/cover/07/b/taemnae-zhycce-uoltera-mici-724288.jpg");
        testMovie8.setReleaseYear(2013);
        testMovie8.setDirector("Бэн Стылер");
        testMovie8.setRating(9.8F);

        return new Object[][] {{testMovie1}, {testMovie2}, {testMovie3}, {testMovie4}, {testMovie5}, {testMovie6}, {testMovie8}};
    }

    @DataProvider(name = "movie_provider1")
    public Object[][] passMovie() {
        Movie testMovie1 = new Movie();
        testMovie1.setTitle("Avengers");
        testMovie1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity. ");
        testMovie1.setPoster("https://upload.wikimedia.org/wikipedia/en/f/f9/TheAvengers2012Poster.jpg");
        testMovie1.setReleaseYear(2012);
        testMovie1.setDirector("Anthony Russo");
        testMovie1.setRating(8.0F);

        return new Object[][] {{testMovie1}};
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
    public void selectAllMoviesTest() throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        List<Movie> movies = dao.selectAll(0, 0);
        Assert.assertEquals(movies.size(), 8);
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

        List<Movie> movies = dao.selectAll(0,0);
        Assert.assertEquals(movies.size(), newAmount);
    }

    @Test
    public void removeAllTest() throws DaoException {
        MovieDao dao = new MovieDaoImpl();
        dao.deleteAll();

        List<Movie> movies = dao.selectAll(0, 0);
        Assert.assertEquals(movies.size(), 0);
    }
}
