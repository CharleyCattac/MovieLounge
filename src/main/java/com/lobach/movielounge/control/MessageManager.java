package com.lobach.movielounge.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageManager {
    private static final Logger logger = LogManager.getLogger();

    public static String getMessage(String bundleName, String propertyName) {
        String message = "";
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
            message = bundle.getString(propertyName);
        } catch (MissingResourceException e) {
            logger.error(String.format("Resource not found: %s", bundleName));
        }
        return message;
    }
}
