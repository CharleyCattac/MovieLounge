package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.model.Booking;
import com.lobach.movielounge.service.BookingService;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.impl.BookingServiceImpl;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;

public class MakeBookingCommand implements ActionCommand {
    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_EVENT_PAGE = "path.movieEvents";

    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_AMOUNT_ERROR_MESSAGE = "booking.amount";
    private static final String PROPERTY_BOOKING_ERROR_MESSAGE = "error.failed.add.booking";
    private static final String PROPERTY_EVENT_ERROR_MESSAGE = "error.failed.update.booking_amount";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_EVENT_ID = "event_id";
    private static final String PARAMETER_AMOUNT = "amount";
    private static final String PARAMETER_CURRENT_AMOUNT = "current_amount";

    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private MovieEventService movieEventService;
    private BookingService bookingService;
    private ShowEventsCommand showEventsCommand;

    public MakeBookingCommand() {
        movieEventService = new MovieEventServiceImpl();
        bookingService = new BookingServiceImpl();
        showEventsCommand = new ShowEventsCommand();
    }

    @Override
    public String execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_EVENT_PAGE);
        LocaleType localeType = defineLocale(content);
        content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);

        int bookingAmount;
        try {
            bookingAmount = Integer.parseInt(content.getRequestParameter(PARAMETER_AMOUNT));
        } catch (IllegalArgumentException e) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_AMOUNT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to make booking because of amount: ", e);
            return page;
        }

        // FIXME: 2/17/2020 command transaction???

        long userId = Long.parseLong(content.getRequestParameter(PARAMETER_USER_ID));
        long eventId = Long.parseLong(content.getRequestParameter(PARAMETER_EVENT_ID));
        int currentAmount = Integer.parseInt(content.getRequestParameter(PARAMETER_CURRENT_AMOUNT));
        Booking booking = null;

        try {
            movieEventService.increaseBookingAmountById(eventId, currentAmount, bookingAmount);
        } catch (ServiceException e) {
            page = showEventsCommand.execute(content);
            logger.error("Failed to increase booking amount: ", e);
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_EVENT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            return page;
        }

        try {
            booking = bookingService.findBookingByIds(userId, eventId);
            if (booking == null) {
                bookingService.makeNewBooking(userId, eventId, bookingAmount);
            } else {
                bookingService.increaseAmount(booking.getId(), booking.getAmount(), bookingAmount);
            }
        } catch (ServiceException e) {
            logger.error("Failed to make booking: ", e);
            try {
                movieEventService.decreaseBookingAmountById(eventId, currentAmount, bookingAmount);
            } catch (ServiceException e1) {
                page = showEventsCommand.execute(content);
                String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
                return page;
            }
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_BOOKING_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
        }

        page = showEventsCommand.execute(content);
        return page;
    }
}
