package com.lobach.movielounge.timertask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimerTaskSceduler {
    private static final Logger logger = LogManager.getLogger();

    private static AtomicBoolean isSet = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

    private static final long HOURS_24 = 900_000L * 4 * 24;
    private static DeleteOutdatedEventsTask deleteOutdatedEventsTask;

    public static void setUpTimerTasks() {
        lock.lock();
        if (!isSet.get()) {
            setUpOutdatedEventsTask();
            isSet.set(true);
        }
        lock.unlock();
    }

    TimerTaskSceduler() {
    }

    private static void setUpOutdatedEventsTask() {
        if (deleteOutdatedEventsTask == null) {
            deleteOutdatedEventsTask = DeleteOutdatedEventsTask.getInstance();
            Timer timer = new Timer("Delete outdated events");
            timer.scheduleAtFixedRate(deleteOutdatedEventsTask, 0, HOURS_24);
            logger.info("Delete outdated events task has been set");
        }
    }
}
