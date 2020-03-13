package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.Booking;
import com.lobach.movielounge.model.User;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.service.BookingService;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.UserService;
import com.lobach.movielounge.service.impl.BookingServiceImpl;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.UserServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.util.Router;

import java.util.ArrayList;
import java.util.List;

public class OpenProfileCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";
    private static final String PROPERTY_PROFILE_PAGE = "path.profile_page";

    private static final String PROPERTY_ACCESS_MESSAGE = "error.invalid_access";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_TAB = "tab";
    private static final String PARAMETER_TAB_USER = "user";
    private static final String PARAMETER_TAB_BOOKING = "booking";

    private static final String ATTRIBUTE_BOOKINGS = "bookings";
    private static final String ATTRIBUTE_BOOKINGS_SIZE = "bookingSize";
    private static final String ATTRIBUTE_USERS = "users";
    private static final String ATTRIBUTE_USERS_SIZE = "usersSize";

    private static final String ATTRIBUTE_USER_ERROR_MESSAGE = "userErrorMessage";
    private static final String ATTRIBUTE_BOOKING_ERROR_MESSAGE = "bookingErrorMessage";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_BOOKING_ID = "bookingId";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final UserRole UNEXPECTED_ROLE = UserRole.GUEST;

    private MovieEventService eventService;
    private BookingService bookingService;
    private UserService userService;

    public OpenProfileCommand() {
        eventService = new MovieEventServiceImpl();
        bookingService = new BookingServiceImpl();
        userService = new UserServiceImpl();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_PROFILE_PAGE);
        Router router = new Router(page);
        LocaleType localeType = defineLocale(content);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        if (rolesMatch(content, UNEXPECTED_ROLE)) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ACCESS_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, errorMessage);
            logger.error("Failed to open profile because of invalid access rights.");
        } else {
            UserRole actualRole = (UserRole) content.getSessionAttribute(ATTRIBUTE_ROLE);
            String requestedTab = content.getRequestParameter(PARAMETER_TAB);
            try {
                if (actualRole == UserRole.ADMIN || actualRole == UserRole.USER) {
                    if (requestedTab == null || requestedTab.equals(PARAMETER_TAB_BOOKING)) {
                        List<Booking> bookings;
                        content.setRequestAttribute(PARAMETER_TAB, PARAMETER_TAB_BOOKING);
                        if (actualRole == UserRole.ADMIN) {
                            bookings = bookingService.findAllBookings(0,0);
                        } else {
                            long userId = Long.parseLong(content.getRequestParameter(PARAMETER_USER_ID));
                            bookings = bookingService.findAllUserBookings(userId);
                        }
                        for (Booking booking : bookings) {
                            booking.setMovieEvent(eventService.findEventById(booking.getMovieEventId()));
                            if (actualRole == UserRole.ADMIN) {
                                booking.setUser(userService.findUserById(booking.getUserId()));
                            }
                        }
                        content.setRequestAttribute(ATTRIBUTE_BOOKINGS_SIZE, bookings.size());
                        if (!bookings.isEmpty()) {
                            content.setRequestAttribute(ATTRIBUTE_BOOKINGS, bookings);
                        } else {
                            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                                    PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                            content.setRequestAttribute(ATTRIBUTE_BOOKING_ERROR_MESSAGE, errorMessage);
                        }
                    } else if (requestedTab.equals(PARAMETER_TAB_USER) && actualRole == UserRole.ADMIN) {
                        List<User> users;
                        content.setRequestAttribute(PARAMETER_TAB, PARAMETER_TAB_USER);
                        users = userService.findAllUsers(0, 0);
                        content.setRequestAttribute(ATTRIBUTE_USERS_SIZE, users.size());
                        if (!users.isEmpty()) {
                            content.setRequestAttribute(ATTRIBUTE_USERS, users);
                        } else {
                            String userErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                                    PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                            content.setRequestAttribute(ATTRIBUTE_USER_ERROR_MESSAGE, userErrorMessage);
                        }
                    }
                }
            } catch (ServiceException e) {
                String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
                content.setRequestAttribute(ATTRIBUTE_USER_ERROR_MESSAGE, null);
                logger.error("Failed to retrieve movie events from db: ", e);
            }
        }

        return router;
    }
}
