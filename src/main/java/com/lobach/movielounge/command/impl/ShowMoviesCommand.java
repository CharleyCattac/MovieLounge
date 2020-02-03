package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.Movie;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowMoviesCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_INTERFACE = "interface";
    private static final String PROPERTY_MOVIES = "path.movies";
    private static final String PROPERTY_LIMIT_PER_PAGE = "config.list.limit_per_page";
    private static final String PROPERTY_ERROR_MESSAGE = "message.error.default";

    private static final String ATTRIBUTE_MOVIES = "movies";
    private static final String ATTRIBUTE_MOVIES_SIZE = "movies_size";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "error_message";

    private MovieService service;
    private int limitPerPage; // TODO: 03/02/2020 add pagination

    public ShowMoviesCommand() {
        service = new MovieServiceImpl();

        String limitString = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_LIMIT_PER_PAGE);
        limitPerPage = Integer.parseInt(limitString);
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_MOVIES);
        LocaleType localeType = defineLocale(content);
        List<Movie> movies = new ArrayList<>();
        try {
            movies = service.findAllMovies(0, 0);
            content.setRequestAttribute(ATTRIBUTE_MOVIES, movies);
            content.setRequestAttribute(ATTRIBUTE_MOVIES_SIZE, movies.size());
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
        } catch (ServiceException e) {
            logger.error("Failed to retrieve movies from db: ", e);
            String errorMessage = PropertyManager.getProperty(BUNDLE_INTERFACE, PROPERTY_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
        }
        return page;
    }
}
