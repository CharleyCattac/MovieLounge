package com.lobach.movielounge.database.connection;

import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;

public class ConnectionManagerTest {
    private ConnectionManager manager  = new ConnectionManager();
    private ProxyConnection connection;

    @AfterMethod
    public void destroyConnection() throws SQLException {
        connection.reallyClose();
    }

    @Test(description = "creating ProxyConnection")
    public void getProxyConnectionTest() throws SQLException {
        connection = manager.createConnection();

        Class<?> actual = connection.getClass();
        Class<?> expected = ProxyConnection.class;

        Assert.assertEquals(actual, expected);
    }
}