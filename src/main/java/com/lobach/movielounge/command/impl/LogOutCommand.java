package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;

public class LogOutCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";

    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_MAIN_PAGE);
        content.invalidateSession();
        return page;
    }
}
