package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.util.PageCounter;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.model.MovieEvent;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;
import com.lobach.movielounge.validator.SimpleTypeValidator;

import java.util.List;

import static com.lobach.movielounge.validator.SimpleTypeValidator.SimpleType.INTEGER;

public class ShowEventsCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_LIMIT_PER_PAGE = "config.list.limit_per_page";
    private static final String PROPERTY_EVENTS = "path.movieEvents";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String PARAMETER_PAGE_NUMBER = "pageNumber";

    private static final String ATTRIBUTE_EVENTS = "events";
    private static final String ATTRIBUTE_EVENTS_SIZE = "eventsSize";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private MovieEventService movieEventService;
    private MovieService movieService;
    private int limitPerPage;

    public ShowEventsCommand() {
        movieEventService = new MovieEventServiceImpl();
        movieService = new MovieServiceImpl();

        String limitString = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_LIMIT_PER_PAGE);
        limitPerPage = Integer.parseInt(limitString);
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_EVENTS);
        LocaleType localeType = defineLocale(content);
        List<MovieEvent> movieEvents;

        String pageClickedString = content.getRequestParameter(PARAMETER_PAGE_NUMBER);
        int pageClicked = 0;
        try {
            if (pageClickedString != null
                    && SimpleTypeValidator.stringHasValidValue(INTEGER, pageClickedString)) {
                pageClicked = Integer.parseInt(pageClickedString);
            }

            int moviesTotalAmount = movieEventService.findAllEvents(0,0).size();
            movieEvents = movieEventService.findAllEvents(pageClicked * limitPerPage, limitPerPage);

            if (!movieEvents.isEmpty()) {
                for (MovieEvent event : movieEvents) {
                    for (Long movieId : event.getMovieIds()) {
                        event.getMovies().add(movieService.findById(movieId));
                    }
                }
                content.setRequestAttribute(ATTRIBUTE_EVENTS, movieEvents);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            } else {
                content.setRequestAttribute(ATTRIBUTE_EVENTS, null);
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            content.setRequestAttribute(ATTRIBUTE_EVENTS_SIZE, movieEvents.size());
            int pageCount = PageCounter.countPages(moviesTotalAmount, limitPerPage);
            content.setRequestAttribute(ATTRIBUTE_PAGE_COUNT, pageCount);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            logger.error("Failed to retrieve movie events from db: ", e);
        }

        return new Router(page);
    }
}
