package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.service.MovieService;
import com.lobach.movielounge.service.impl.MovieServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class CreateMovieCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_CREATE_PAGE = "path.create.movie";

    private static final String PROPERTY_ACCESS_MESSAGE = "error.invalid_access";
    private static final String PROPERTY_FAILED_ERROR_MESSAGE = "error.failed.add.movie";

    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_DIRECTOR = "director";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_RATING = "rating";
    private static final String PARAMETER_POSTER = "poster";

    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final UserRole EXPECTED_ROLE = UserRole.ADMIN;

    private MovieService movieService;
    private ShowMoviesCommand showMoviesCommand;

    public CreateMovieCommand() {
        movieService = new MovieServiceImpl();
        showMoviesCommand = new ShowMoviesCommand();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_CREATE_PAGE);
        Router router = new Router(page);
        LocaleType localeType = defineLocale(content);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        if (!rolesMatch(content, EXPECTED_ROLE)) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ACCESS_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to add movie because of invalid access rights.");
            return router;
        }

        String title = content.getRequestParameter(PARAMETER_TITLE);
        String description = content.getRequestParameter(PARAMETER_DESCRIPTION);
        String director = content.getRequestParameter(PARAMETER_DIRECTOR);
        int releaseYear = Integer.parseInt(content.getRequestParameter(PARAMETER_YEAR));
        float rating = Float.parseFloat(content.getRequestParameter(PARAMETER_RATING));
        String posterUrl = content.getRequestParameter(PARAMETER_POSTER);
        try {
            movieService.addMovie(title, description, posterUrl, releaseYear, director, rating);
            router = showMoviesCommand.execute(content);
        } catch (ServiceException e) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_FAILED_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to add movie: ", e);
        }

        return router;
    }
}
