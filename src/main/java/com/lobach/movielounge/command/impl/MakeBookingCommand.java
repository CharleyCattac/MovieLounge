package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.model.Booking;
import com.lobach.movielounge.service.BookingService;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.impl.BookingServiceImpl;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class MakeBookingCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    //private static final String PROPERTY_EVENTS = "path.movieEvents";
    private static final String PROPERTY_EVENTS_REDIRECT = "path.redirect.movieEvents";

    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_ACCESS_MESSAGE = "error.invalid_access";
    private static final String PROPERTY_AMOUNT_ERROR_MESSAGE = "error.failed.update.booking_amount";
    private static final String PROPERTY_BOOKING_ERROR_MESSAGE = "error.failed.add.booking";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_EVENT_ID = "event_id";
    private static final String PARAMETER_AMOUNT = "amount";
    private static final String PARAMETER_CURRENT_AMOUNT = "current_amount";

    private static final String ATTRIBUTE_ERROR_EVENT_ID = "errorEventId";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final UserRole EXPECTED_ROLE = UserRole.USER;

    private MovieEventService movieEventService;
    private BookingService bookingService;
    private ShowEventsCommand showEventsCommand;

    public MakeBookingCommand() {
        movieEventService = new MovieEventServiceImpl();
        bookingService = new BookingServiceImpl();
        showEventsCommand = new ShowEventsCommand();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_EVENTS_REDIRECT);
        LocaleType localeType = defineLocale(content);
        Router router = new Router(page);
        //router.setRouteType(Router.RouteType.REDIRECT);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        if (!rolesMatch(content, EXPECTED_ROLE)) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ACCESS_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to make booking because of invalid access rights.");
            return router;
        }

        boolean amountIncreased = false;
        int bookingAmount = Integer.parseInt(content.getRequestParameter(PARAMETER_AMOUNT));;
        long userId = Long.parseLong(content.getRequestParameter(PARAMETER_USER_ID));
        long eventId = Long.parseLong(content.getRequestParameter(PARAMETER_EVENT_ID));
        int currentAmount = Integer.parseInt(content.getRequestParameter(PARAMETER_CURRENT_AMOUNT));
        Booking booking;

        try {
            if (movieEventService.increaseBookingAmountById(eventId, currentAmount, bookingAmount)) {
                amountIncreased = true;
                booking = bookingService.findBookingByIds(userId, eventId);
                if (booking == null) {
                    bookingService.makeNewBooking(userId, eventId, bookingAmount);
                } else {
                    bookingService.increaseAmount(booking.getId(), booking.getAmount(), bookingAmount);
                }
                router.setRouteType(Router.RouteType.REDIRECT);
            } else {
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_AMOUNT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                content.setRequestAttribute(ATTRIBUTE_ERROR_EVENT_ID, eventId);
                logger.error("Failed to increase booking amount.");
            }
        } catch (ServiceException e) {
            logger.error("Failed to make booking: ", e);
            try {
                if (amountIncreased || movieEventService.decreaseBookingAmountById(eventId, currentAmount, bookingAmount)) {
                    String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                            PROPERTY_BOOKING_ERROR_MESSAGE, localeType);
                    content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                    content.setRequestAttribute(ATTRIBUTE_ERROR_EVENT_ID, eventId);
                } else {
                    String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                            PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                    content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
                    logger.error("Failed to redo event amount change: ", e);
                }
            } catch (ServiceException e1) {
                String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
                logger.error("Failed to fix everything: ", e);
            }
        }

        return showEventsCommand.execute(content);
    }
}
