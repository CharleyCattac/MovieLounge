package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.util.Encrypror;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.service.UserService;
import com.lobach.movielounge.service.impl.UserServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

import java.util.Optional;

public class LogInCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_MAIN_PAGE = "path.main_page";
    private static final String PROPERTY_LOGIN_PAGE = "path.log_in";
    private static final String PROPERTY_ERROR_MESSAGE_DEFAULT = "error.default";
    private static final String PROPERTY_NOT_FOUND_MESSAGE = "user.email_password_do_not_match";

    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_EMAIL = "email";

    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_CURRENT_USER = "currentUser";

    private UserService userService;

    public LogInCommand() {
        userService = new UserServiceImpl();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_LOGIN_PAGE);
        Router router = new Router(page);

        LocaleType localeType = defineLocale(content);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        String email = content.getRequestParameter(PARAMETER_EMAIL);
        String password = content.getRequestParameter(PARAMETER_PASSWORD);

        try {
            Optional<User> currentUser = userService.findUserByEmailAndPassword(email, password);
            if (currentUser.isPresent()) {
                User trueUser = currentUser.get();
                content.setSessionAttribute(ATTRIBUTE_CURRENT_USER, trueUser);
                content.setSessionAttribute(ATTRIBUTE_ROLE, trueUser.getUserRole());
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
                logger.debug(String.format("User %s logged in successfully", trueUser.getName()));

                page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_MAIN_PAGE);
                router = new Router(page);
            } else {
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_NOT_FOUND_MESSAGE, localeType);
                logger.debug(String.format("Failed to log in by %s. Cause: %s", email, errorMessage));

                content.setRequestAttribute(PARAMETER_EMAIL, email);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
        } catch (ServiceException e) {
            logger.error("Failed to log in due exception ", e);
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ERROR_MESSAGE_DEFAULT, localeType);

            content.setRequestAttribute(PARAMETER_EMAIL, email);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, defaultErrorMessage);
        }

        return router;
    }
}
