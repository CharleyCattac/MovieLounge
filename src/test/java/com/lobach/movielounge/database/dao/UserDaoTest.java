package com.lobach.movielounge.database.dao;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.database.dao.impl.UserDaoImpl;
import com.lobach.movielounge.exception.DaoException;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class UserDaoTest {
    private static final Logger logger = LogManager.getLogger();

    @BeforeClass
    public void initConnectionPool() {
        ConnectionPool.INSTANCE.setUpPool();
    }

    @AfterClass
    public void destroyConnectionPool() {
        //ConnectionManager.INSTANCE.destroyPool();
    }

    @DataProvider(name = "user_provider")
    public Object[][] passUsers() {
        User testUser1 = new User();
        testUser1.setEmail("ren.n.lol.l.sama@gmail.com");
        testUser1.setPassword("RenHasAdminPass");
        testUser1.setName("Renata Lobach");
        testUser1.setUserRole(UserRole.ADMIN);

        User testUser2 = new User();
        testUser2.setEmail("libertyN@gmail.com");
        testUser2.setPassword("kfdjnvkej4kjnf");
        testUser2.setName("Lora Cringe");
        testUser2.setUserRole(UserRole.USER);

        return new Object[][] {{testUser1}, {testUser2}};
    }

    @Test (dataProvider = "user_provider", expectedExceptions = DaoException.class)
    public void insertUsersTest(User testUser) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.insert(testUser);
    }

    @DataProvider(name = "id_provider")
    public Object[][] passId() {
        return new Object[][] {{1, "Renata Lobach"}, {6, "Lora Cringe"}};
    }

    @Test (dataProvider = "id_provider")
    public void getUsersByIdTest(long id, String name) throws DaoException {
        UserDao dao = new UserDaoImpl();
        User user = dao.selectById(id);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getName(), name);
    }

    @DataProvider(name = "email_provider")
    public Object[][] passEmail() {
        return new Object[][] {
                {"ren.n.lol.l.sama@gmail.com", "Renata Lobach"},
                {"libertyN@gmail.com", "Lena Problems"}
        };
    }

    @Test (dataProvider = "email_provider")
    public void getUsersByEmailTest(String email, String name) throws DaoException {
        UserDao dao = new UserDaoImpl();
        User user = dao.selectByEmail(email);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getName(), name);
    }

    @DataProvider(name = "update_user_provider")
    public Object[][] passUserData() {
        User testUser1 = new User();
        testUser1.setId(1L);
        testUser1.setEmail("ren.n.lol.l.sama@gmail.com");
        testUser1.setPassword("123457ren");
        testUser1.setName("Renata Lobach");
        testUser1.setPhoneNumber("375291813110");
        testUser1.setUserRole(UserRole.ADMIN);
        testUser1.setAvatarURL("https://sun9-10.userapi.com/c848636/v848636681/e353/q1oou2oz-cA.jpg");

        User testUser2 = new User();
        testUser2.setId(6L);
        testUser2.setEmail("libertyN@gmail.com");
        testUser2.setPassword("127lora");
        testUser2.setName("Lena Problems");
        testUser2.setPhoneNumber("375331234567");
        testUser2.setUserRole(UserRole.USER);
        testUser2.setAvatarURL("https://i1.sndcdn.com/artworks-000362787813-61v4h3-t500x500.jpg");
        return new Object[][] {
                {"ren.n.lol.l.sama@gmail.com", testUser1},
                {"libertyN@gmail.com", testUser2}
        };
    }

    @Test (dataProvider = "update_user_provider")
    public void updateUserDataTest(String email, User userData) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.updateByEmail(email, userData.getName(), userData.getPhoneNumber(), userData.getAvatarURL());

        User user = dao.selectByEmail(email);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user, userData);
    }

    @DataProvider(name = "password_provider")
    public Object[][] passPassword() {
        return new Object[][] {
                {"ren.n.lol.l.sama@gmail.com", "123457ren"},
                {"libertyN@gmail.com", "127lora"}
        };
    }

    @Test (dataProvider = "password_provider")
    public void updatePasswordTest(String email, String password) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.updatePassword(email, password);

        User user = dao.selectByEmail(email);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getPassword(), password);
    }

    @DataProvider(name = "status_provider")
    public Object[][] passStatus() {
        return new Object[][] {
                {"ren.n.lol.l.sama@gmail.com", UserStatus.ACTIVE},
                {"libertyN@gmail.com", UserStatus.BANNED},
                {"libertyN@gmail.com", UserStatus.ACTIVE}
        };
    }

    @Test (dataProvider = "status_provider")
    public void updateStatusTest(String email, UserStatus status) throws DaoException {
        UserDao dao = new UserDaoImpl();
        dao.updateStatus(email, status.value);

        User user = dao.selectByEmail(email);
        logger.info(String.format("User found: %s", user));
        Assert.assertEquals(user.getStatus(), status);
    }
}
