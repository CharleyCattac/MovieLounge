package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class ChangeLocaleCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_MAIN_PAGE);
        String requestedLocale = content.getRequestParameter(ATTRIBUTE_LOCALE);
        if (requestedLocale != null) {
            content.setSessionAttribute(ATTRIBUTE_LOCALE, requestedLocale);
        }
        return new Router(page);
    }
}
