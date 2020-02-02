package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLocaleCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_NAME = "config";
    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    private static final String ATTRIBUTE_LOCALE = "locale";
    private static final String ATTRIBUTE_PAGE = "page";

    @Override
    public String execute(RequestContent content) {
        String page = (String) content.getSessionAttribute(ATTRIBUTE_PAGE);
        if (page == null) {
            logger.debug("Attr PAGE in null -> forward to main page");
            page = PropertyManager.getProperty(BUNDLE_NAME, PROPERTY_MAIN_PAGE);
            content.setSessionAttribute(ATTRIBUTE_PAGE, page);
        }
        String requestedLocale = content.getRequestParameter(ATTRIBUTE_LOCALE);
        if (requestedLocale != null) {
            content.setSessionAttribute(ATTRIBUTE_LOCALE, requestedLocale);
            logger.debug(String.format("Final locale: %s", requestedLocale));
        }
        return page;
    }
}
