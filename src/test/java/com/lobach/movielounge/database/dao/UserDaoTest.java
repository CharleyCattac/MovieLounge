package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.impl.UserDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.UserFactory;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class UserDaoTest {
    private static final Logger logger = LogManager.getLogger();
    private static final UserFactory factory = UserFactory.INSTANCE;

    @BeforeClass
    public void initConnectionPool() {
        ConnectionPool.INSTANCE.setUpPool();
    }

    @AfterClass
    public void destroyConnectionPool() {
        ConnectionPool.INSTANCE.destroyPool();
    }

    @DataProvider(name = "user_provider")
    public Object[][] passUsers() {
        User testUser1 = factory.createFullNoId(
                "ren.n.lol.l.sama@gmail.com",
                "RenHasAdminPass",
                UserRole.ADMIN,
                UserStatus.ACTIVE,
                "Renata Lobach",
                "375291813110",
                "https://static.grouple.co/uploads/pics/05/72/935.jpg"
        );

        User testUser2 = factory.createBasic(
                "libertyN@gmail.com",
                "libertyN@gmail.com",
                UserRole.USER,
                UserStatus.ACTIVE
        );
        testUser2.setName("Lora Cringe");
        testUser2.setAvatarURL("https://www.doctorsataustraliafair.com.au/wp-content/uploads/2019/07/Unknown-person.png");

        return new Object[][] {{testUser1}, {testUser2}};
    }

    @Test (dataProvider = "user_provider", expectedExceptions = DaoException.class)
    public void insertUsersTest(User testUser) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.add(testUser);
    }

    @DataProvider(name = "id_provider")
    public Object[][] passId() {
        return new Object[][] {{1, "Renata Lobach"}, {6, "Lora Cringe"}};
    }

    @Test (dataProvider = "id_provider")
    public void getUsersByIdTest(long id, String name) throws DaoException {
        UserDao dao = new UserDaoImpl();
        User user = dao.findById(id);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getName(), name);
    }

    @DataProvider(name = "password_provider")
    public Object[][] passPassword() {
        return new Object[][] {
                {1L, "123457ren"},
                {6L, "127lora"}
        };
    }

    @Test (dataProvider = "password_provider")
    public void updatePasswordTest(long id, String password) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.updatePassword(id, password);

        User user = dao.findById(id);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getPassword(), password);
    }

    @DataProvider(name = "status_provider")
    public Object[][] passStatus() {
        return new Object[][] {
                {1, UserStatus.ACTIVE},
                {6, UserStatus.ACTIVE}
        };
    }

    @Test (dataProvider = "status_provider")
    public void updateStatusTest(long id, UserStatus status) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.updateStatus(id, status.value);

        User user = dao.findById(id);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getStatus(), status);
    }

    @Test
    public void findAllUsersTest() throws DaoException {
        UserDao dao = new UserDaoImpl();
        List<User> users = dao.findAll(0, 0);

        for (User user : users) {
            logger.info(String.format("User found: %s", user));
        }
        Assert.assertEquals(users.size(), 2);
    }
}
