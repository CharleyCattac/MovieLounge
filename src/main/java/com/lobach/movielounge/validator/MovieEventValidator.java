package com.lobach.movielounge.validator;

import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.model.MovieEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class MovieEventValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String BUNDLE_STARTUP_NAME = "config";
    private static final String PROPERTY_MAX_AMOUNT = "config.max_booking_amount";
    private static final long MILLIS_IN_WEEK = 7 * 24 * 60 * 60 * 1000L;

    private static int maxBookingAmount;

    static {
        try {
            String value = PropertyManager.getProperty(BUNDLE_STARTUP_NAME, PROPERTY_MAX_AMOUNT);
            maxBookingAmount = Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            logger.error("Parameter max booking amount is uninitialised");
        }
    }

    public static boolean isOutdated(MovieEvent movieEvent) {
        Date currentDate = new Date();
        return currentDate.after(movieEvent.getDate());
    }

    public static boolean isAtLeastInAWeek(Date eventDate) {
        return new Date(eventDate.getTime() - MILLIS_IN_WEEK).after(new Date(System.currentTimeMillis()));
    }

    public static boolean isFulled(MovieEvent movieEvent) {
        return movieEvent.getBookingAmount() < maxBookingAmount;
    }

    public static boolean amountCanBeIncreased(int currentAmount, int add) {
        return currentAmount + add <= maxBookingAmount;
    }

    public static boolean amountCanBeDecreased(int currentAmount, int sub) {
        return currentAmount - sub >= 0;
    }

    public static int getMaxBookingAmount() {
        return maxBookingAmount;
    }
}
