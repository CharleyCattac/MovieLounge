package com.lobach.movielounge.servlet;

import com.lobach.movielounge.database.connection.ConnectionPool;
import com.lobach.movielounge.timertask.TimerTaskSceduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.INSTANCE.setUpPool();
        TimerTaskSceduler.setUpTimerTasks();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
