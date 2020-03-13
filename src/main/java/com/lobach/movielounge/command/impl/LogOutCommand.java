package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class LogOutCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String PROPERTY_INDEX = "path.index";

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_INDEX);
        content.invalidateSession();

        Router router = new Router(page);
        router.setRouteType(Router.RouteType.REDIRECT);
        return router;
    }
}
