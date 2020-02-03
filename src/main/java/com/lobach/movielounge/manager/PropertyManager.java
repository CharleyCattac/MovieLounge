package com.lobach.movielounge.manager;

import com.lobach.movielounge.command.LocaleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyManager {
    private static final Logger logger = LogManager.getLogger();
    private static LocaleType defaultLocaleType = LocaleType.EN;

    public static String getProperty(String bundleName, String propertyName) {
        return getProperty(bundleName, propertyName, defaultLocaleType);
    }

    public static String getProperty(String bundleName, String propertyName,
                                     LocaleType localeType) {
        Locale locale = Locale.forLanguageTag(localeType.property);
        String message = "";
        try {
            ResourceBundle bundle;
            if (locale != null) {
                bundle = ResourceBundle.getBundle(bundleName, locale);
            } else {
                bundle = ResourceBundle.getBundle(bundleName);
            }
            message = bundle.getString(propertyName);
        } catch (MissingResourceException e) {
            logger.error("Resource not found: ", e);
        }
        return message;
    }
}
