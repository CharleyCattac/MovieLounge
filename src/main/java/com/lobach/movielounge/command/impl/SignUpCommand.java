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

import javax.print.DocFlavor;
import java.beans.Encoder;
import java.util.Optional;

public class SignUpCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_MAIN_PAGE = "path.main_page";
    private static final String PROPERTY_SIGN_UP_PAGE = "path.sign_up";

    private static final String PROPERTY_ERROR_MESSAGE_DEFAULT = "error.default";

    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_AVATAR = "avatar";

    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    private UserService userService;

    public SignUpCommand() {
        userService = new UserServiceImpl();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_SIGN_UP_PAGE);
        Router router = new Router(page);
        //router.setRouteType(Router.RouteType.NOTHING);

        LocaleType localeType = defineLocale(content);
        String email = content.getRequestParameter(PARAMETER_EMAIL);
        String password = content.getRequestParameter(PARAMETER_PASSWORD);
        String name = content.getRequestParameter(PARAMETER_NAME);
        String phoneNumber = content.getRequestParameter(PARAMETER_PHONE);
        String avatarUrl = content.getRequestParameter(PARAMETER_AVATAR);

        try {
            String possibleErrorPath = userService.registerUser(email, password, name, phoneNumber, avatarUrl);
            if (possibleErrorPath == null) {
                logger.info(String.format("New user: %s", email));
                router = new LogInCommand().execute(content);
            } else {
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        possibleErrorPath, localeType);
                logger.debug(String.format("Failed to add user: %s. Cause: %s", email, errorMessage));

                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                content.setRequestAttribute(PARAMETER_EMAIL, email);
                content.setRequestAttribute(PARAMETER_NAME, name);
                content.setRequestAttribute(PARAMETER_PHONE, phoneNumber);
                content.setRequestAttribute(PARAMETER_AVATAR, avatarUrl);
            }
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            logger.error("Failed to add user: ", e);
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ERROR_MESSAGE_DEFAULT, localeType);

            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(PARAMETER_EMAIL, email);
            content.setRequestAttribute(PARAMETER_NAME, name);
            content.setRequestAttribute(PARAMETER_PHONE, phoneNumber);
            content.setRequestAttribute(PARAMETER_AVATAR, avatarUrl);
        }

        return router;
    }
}
