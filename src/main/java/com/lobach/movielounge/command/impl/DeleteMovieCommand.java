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

public class DeleteMovieCommand implements ActionCommand {
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_ACCESS_MESSAGE = "error.invalid_access";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";

    private static final String PARAMETER_MOVIE_ID = "movie_id";

    private static final String ATTRIBUTE_ERROR_MOVIE_ID = "errorMovieId";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final UserRole EXPECTED_ROLE = UserRole.ADMIN;

    private MovieService movieService;
    private ShowMoviesCommand showMoviesCommand;

    public DeleteMovieCommand() {
        movieService = new MovieServiceImpl();
        showMoviesCommand = new ShowMoviesCommand();
    }

    @Override
    public Router execute(RequestContent content) {
        LocaleType localeType = defineLocale(content);

        if (!rolesMatch(content, EXPECTED_ROLE)) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ACCESS_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to delete movie because of invalid access rights.");
        } else {
            long movieId = Long.parseLong(content.getRequestParameter(PARAMETER_MOVIE_ID));
            try {
                movieService.deleteById(movieId);
            } catch (ServiceException e) {
                String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, defaultErrorMessage);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MOVIE_ID, movieId);
                logger.error(String.format("Failed to delete movie %d: ", movieId), e);
            }
        }

        return showMoviesCommand.execute(content);
    }
}
