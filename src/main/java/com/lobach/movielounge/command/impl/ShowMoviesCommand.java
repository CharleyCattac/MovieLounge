package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;

import java.util.List;

public class ShowMoviesCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_MOVIES_PATH = "path.movies";
    private static final String PROPERTY_LIMIT_PER_PAGE = "config.list.limit_per_page";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String ATTRIBUTE_MOVIES = "movies";
    private static final String ATTRIBUTE_MOVIES_SIZE = "moviesSize";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private MovieService service;
    private int limitPerPage; // todo: 03/02/2020 add pagination

    public ShowMoviesCommand() {
        service = new MovieServiceImpl();

        String limitString = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_LIMIT_PER_PAGE);
        limitPerPage = Integer.parseInt(limitString);
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_MOVIES_PATH);
        LocaleType localeType = defineLocale(content);
        List<Movie> movies;
        try {
            movies = service.findAllMovies(0, 0);
            if (!movies.isEmpty()) {
                content.setRequestAttribute(ATTRIBUTE_MOVIES, movies);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            } else {
                content.setRequestAttribute(ATTRIBUTE_MOVIES, null);
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            content.setRequestAttribute(ATTRIBUTE_MOVIES_SIZE, movies.size());
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            logger.error("Failed to retrieve movies from db: ", e);
        }
        return page;
    }
}
