package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowEventsCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_INTERFACE = "interface";
    private static final String PROPERTY_EVENTS = "path.events";
    private static final String PROPERTY_ERROR_MESSAGE = "message.error.default";

    private static final String ATTRIBUTE_EVENTS = "events";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "error_message";

    private MovieEventService movieEventService;
    private MovieService movieService;

    public ShowEventsCommand() {
        movieEventService = new MovieEventServiceImpl();
        movieService = new MovieServiceImpl();
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_EVENTS);
        LocaleType localeType = defineLocale(content);
        List<MovieEvent> movieEvents = new ArrayList<>();
        try {
            movieEvents = movieEventService.findAllEvents(0,0);
            for (MovieEvent event : movieEvents) {
                for (Long movieId : event.getMovieIds()) {
                    event.getMovies().add(movieService.findById(movieId));
                }
            }
            content.setRequestAttribute(ATTRIBUTE_EVENTS, movieEvents);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
        } catch (ServiceException e) {
            logger.error("Failed to retrieve events from db: ", e);
            String errorMessage = PropertyManager.getProperty(BUNDLE_INTERFACE, PROPERTY_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
        }
        return page;
    }
}
