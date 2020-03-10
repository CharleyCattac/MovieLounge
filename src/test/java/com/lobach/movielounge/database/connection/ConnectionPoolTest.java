package com.lobach.movielounge.database.connection;

import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolTest {
    private ConnectionPool pool = ConnectionPool.INSTANCE;
    private int poolSize;
    List<ProxyConnection> connectionList = new ArrayList<>(poolSize);

    @BeforeClass
    public void setUpPool() {
        pool.setUpPool();
        poolSize = pool.getPoolSize();
    }

    @AfterClass
    public void destroyPool() {
        if (!connectionList.isEmpty()) {
            for (ProxyConnection connection : connectionList) {
                pool.releaseConnection(connection);
            }
            connectionList.clear();
        }
        pool.destroyPool();
    }

    @Test(description = "retrieving available connections count without occupying any")
    public void getAvailableCountNoUsageTest() throws SQLException {
        int actualSize = pool.getAvailableConnectionsCount();
        int expectedSize = poolSize;

        Assert.assertEquals(actualSize, expectedSize);
    }

    @Test(description = "retrieving occupied connections count without occupying any")
    public void getOccupiedCountNoUsageTest() throws SQLException {
        int actualSize = pool.getOccupiedConnectionsCount();
        int expectedSize = 0;

        Assert.assertEquals(actualSize, expectedSize);
    }

    @Test(description = "retrieving available connections count with occupying all of them")
    public void getAvailableCountAllUsedTest() throws SQLException {
        for (int i = 0; i < poolSize; i++) {
            connectionList.add(pool.occupyConnection());
        }

        int actualSize = pool.getAvailableConnectionsCount();
        int expectedSize = 0;

        Assert.assertEquals(actualSize, expectedSize);
    }

    @Test(description = "retrieving occupied connections count with occupying all of them")
    public void getOccupiedCountAllUsedTest() throws SQLException {
        for (int i = 0; i < poolSize; i++) {
            connectionList.add(pool.occupyConnection());
        }

        int actualSize = pool.getOccupiedConnectionsCount();
        int expectedSize = poolSize;

        Assert.assertEquals(actualSize, expectedSize);
    }
}
