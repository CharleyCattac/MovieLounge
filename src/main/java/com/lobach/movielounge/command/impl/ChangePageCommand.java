package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;

public class ChangePageCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    private static final String PARAMETER_PAGE= "page";

    @Override
    public String execute(RequestContent contentManager) {
        String pageProperty = contentManager.getRequestParameter(PARAMETER_PAGE);
        if (pageProperty == null) {
            logger.debug("Parameter PAGE in null -> forward to main page");
            pageProperty = PROPERTY_MAIN_PAGE;
        }
        return PropertyManager.getProperty(BUNDLE_CONFIG, pageProperty);
    }
}
