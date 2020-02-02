package com.lobach.movielounge.validator;

import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.MovieSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class MovieSessionValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String BUNDLE_STARTUP_NAME = "config";
    private static final String PROPERTY_MAX_AMOUNT = "config.max_booking_amount";

    private static int maxBookingAmount;

    static {
        try {
            String value = PropertyManager.getProperty(BUNDLE_STARTUP_NAME, PROPERTY_MAX_AMOUNT);
            maxBookingAmount = Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            logger.error("Parameter max booking amount is uninitialised");
        }
    }

    public boolean isOutdated(MovieSession movieSession) {
        Date currentDate = new Date();
        return currentDate.after(movieSession.getDate());
    }

    public boolean isFulled(MovieSession movieSession) {
        return movieSession.getBookingAmount() < maxBookingAmount;
    }

    public static int getMaxBookingAmount() {
        return maxBookingAmount;
    }
}
