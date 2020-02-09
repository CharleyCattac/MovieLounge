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

import java.util.List;

public class ShowEventsCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_EVENTS = "path.movieEvents";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String ATTRIBUTE_EVENTS = "events";
    private static final String ATTRIBUTE_EVENTS_SIZE = "eventsSize";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

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
        List<MovieEvent> movieEvents;
        try {
            movieEvents = movieEventService.findAllEvents(0,0);
            if (!movieEvents.isEmpty()) {
                for (MovieEvent event : movieEvents) {
                    for (Long movieId : event.getMovieIds()) {
                        event.getMovies().add(movieService.findById(movieId));
                    }
                }
                content.setRequestAttribute(ATTRIBUTE_EVENTS, movieEvents);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
            } else {
                content.setRequestAttribute(ATTRIBUTE_EVENTS, null);
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            content.setRequestAttribute(ATTRIBUTE_EVENTS_SIZE, movieEvents.size());
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            logger.error("Failed to retrieve events from db: ", e);
        }
        return page;
    }
}
