package com.lobach.movielounge.model.validator;

import com.lobach.movielounge.model.entity.MovieSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum MovieSessionValidator {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String BUNDLE_NAME = "startup";
    private static final String PROPERTY_MAX_AMOUNT = "max_booking_amount";
    private static final String MESSAGE_OUTDATED = "Session is outdated";
    private static final String MESSAGE_FULLED = "Session is fulled (out of free seats)";

    private static int maxBookingAmount;
    private String message;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            maxBookingAmount = (int) bundle.getObject(PROPERTY_MAX_AMOUNT);
        } catch (MissingResourceException e) {
            logger.error(String.format("Resource not found: %s", BUNDLE_NAME));
        }
    }

    MovieSessionValidator() {
        message = "";
    }

    public boolean validate(MovieSession movieSession) {
        boolean valid = true;
        if (isOutdated(movieSession)) {
            valid = false;
            message = MESSAGE_OUTDATED;
        } else if (isFulled(movieSession)) {
            valid = false;
            message = MESSAGE_FULLED;
        }

        movieSession.setAvailable(valid);
        return valid;
    }

    private boolean isOutdated(MovieSession movieSession) {
        Date currentDate = new Date();
        return currentDate.after(movieSession.getDate());
    }

    private boolean isFulled(MovieSession movieSession) {
        return movieSession.getBookingAmount() < maxBookingAmount;
    }

    public String getMessage() {
        return message;
    }
}
