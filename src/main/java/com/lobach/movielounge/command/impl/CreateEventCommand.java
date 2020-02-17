package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateEventCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_CREATE_PAGE = "path.create.event";

    private static final String PROPERTY_DATETIME_ERROR_MESSAGE = "event.date.format";
    private static final String PROPERTY_MOVIE_ERROR_MESSAGE = "event.movie.title";
    private static final String PROPERTY_FAILED_ERROR_MESSAGE = "error.failed.add.event";

    private static final String PARAMETER_DATE = "date";
    private static final String PARAMETER_HOURS = "hours";
    private static final String PARAMETER_MINUTES = "minutes";
    private static final String PARAMETER_THEME = "theme";
    private static final String PARAMETER_MOVIE1 = "movie1";
    private static final String PARAMETER_MOVIE2 = "movie2";
    private static final String PARAMETER_MOVIE3 = "movie3";

    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private static final long MILLIS_IN_MINUTE = 60 * 1000L;
    private static final long MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;
    private static final long MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR;
    private static final long MILLIS_IN_MONTH = 31 * MILLIS_IN_DAY;

    private MovieEventService movieEventService;
    private MovieService movieService;
    private ShowEventsCommand showEventsCommand;

    public CreateEventCommand() {
        movieEventService = new MovieEventServiceImpl();
        movieService = new MovieServiceImpl();
        showEventsCommand = new ShowEventsCommand();
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_CREATE_PAGE);
        LocaleType localeType = defineLocale(content);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        Date date;
        try {
            date = format.parse(content.getRequestParameter(PARAMETER_DATE));
            long hoursMillis = Integer.parseInt(content.getRequestParameter(PARAMETER_HOURS))
                    * MILLIS_IN_HOUR;
            long minutesMillis = (Integer.parseInt(content.getRequestParameter(PARAMETER_MINUTES)) - 2)
                    * MILLIS_IN_MINUTE;
            date.setTime(date.getTime() + MILLIS_IN_MONTH + hoursMillis + minutesMillis);
        } catch (ParseException | IllegalArgumentException e) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DATETIME_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to add event because of datetime: ", e);
            return page;
        }
        String theme = content.getRequestParameter(PARAMETER_THEME);
        List<String> movieTitles = new ArrayList<>(3);
        movieTitles.add(content.getRequestParameter(PARAMETER_MOVIE1));
        movieTitles.add(content.getRequestParameter(PARAMETER_MOVIE2));
        movieTitles.add(content.getRequestParameter(PARAMETER_MOVIE3));
        try {
            List<Long> movieIds = new ArrayList<>(3);
            for (String title : movieTitles) {
                long movieId = movieService.findIdByTitle(title);
                if (movieId == 0L) {
                    String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                            PROPERTY_MOVIE_ERROR_MESSAGE, localeType);
                    content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                    logger.error(String.format("Failed to add event (invalid movie title)): %s", title));
                    return page;
                }
                movieIds.add(movieId);
            }
            movieEventService.createEvent(date, theme, movieIds);
            page = showEventsCommand.execute(content);
        } catch (ServiceException e) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_FAILED_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to add event: ", e);
        }
        return page;
    }
}
