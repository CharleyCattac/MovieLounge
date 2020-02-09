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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteEventCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_EVENTS = "path.movieEvents";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String PARAMETER_EVENT_ID = "event_id";

    private static final String ATTRIBUTE_EVENTS = "events";
    private static final String ATTRIBUTE_EVENTS_SIZE = "eventsSize";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private MovieEventService movieEventService;

    public DeleteEventCommand() {
        movieEventService = new MovieEventServiceImpl();
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_EVENTS);
        LocaleType localeType = defineLocale(content);
        long eventId = Long.parseLong(content.getRequestParameter(PARAMETER_EVENT_ID));
        // FIXME: 10/02/2020 get not null list ftom attrs
        List<MovieEvent> movieEvents = (ArrayList<MovieEvent>) content.getRequestAttribute(ATTRIBUTE_EVENTS);
        try {
            movieEventService.deleteSessionById(eventId);
            for (MovieEvent event : movieEvents) {
                if (event.getId() == eventId) {
                    movieEvents.remove(event);
                    break;
                }
            }
            if (movieEvents.isEmpty()) {
                content.setRequestAttribute(ATTRIBUTE_EVENTS, null);
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            } else {
                content.setRequestAttribute(ATTRIBUTE_EVENTS, movieEvents);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            }
            content.setRequestAttribute(ATTRIBUTE_EVENTS_SIZE, movieEvents.size());
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            logger.error(String.format("Failed to delete event %d: ", eventId), e);
        }
        return page;
    }
}
