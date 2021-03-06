package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class ChangePageCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    private static final String PARAMETER_PAGE= "page";

    @Override
    public Router execute(RequestContent content) {
        String pagePath = content.getRequestParameter(PARAMETER_PAGE);
        if (pagePath == null || pagePath.isBlank()) {
            pagePath = PROPERTY_MAIN_PAGE;
        }
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, pagePath);
        return new Router(page);
    }
}
