package com.lobach.movielounge.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyManager {
    private static final Logger logger = LogManager.getLogger();

    public static String getProperty(String bundleName, String propertyName) {
        String message = "";
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
            message = bundle.getString(propertyName);
        } catch (MissingResourceException e) {
            logger.error("Resource not found: ", e);
        }
        return message;
    }
}
