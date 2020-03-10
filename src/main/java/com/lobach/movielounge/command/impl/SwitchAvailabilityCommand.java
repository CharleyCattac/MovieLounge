package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.manager.PropertyManager;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;

public class SwitchAvailabilityCommand implements ActionCommand {
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";

    private static final String PARAMETER_EVENT_ID = "event_id";
    private static final String PARAMETER_EVENT_AVAILABILITY = "event_availability";

    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private MovieEventService movieEventService;
    private ShowEventsCommand showEventsCommand;

    public SwitchAvailabilityCommand() {
        movieEventService = new MovieEventServiceImpl();
        showEventsCommand = new ShowEventsCommand();
    }

    @Override
    public String execute(RequestContent content) {
        String page = null;
        LocaleType localeType = defineLocale(content);
        long eventId = Long.parseLong(content.getRequestParameter(PARAMETER_EVENT_ID));
        boolean eventAvailability = Boolean.parseBoolean(content.getRequestParameter(PARAMETER_EVENT_AVAILABILITY));
        try {
            movieEventService.switchAvailabilityById(eventId, eventAvailability);
            page = showEventsCommand.execute(content);
        } catch (ServiceException e) {
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            logger.error(String.format("Failed to switch event availability %d: ", eventId), e);
        }
        return page;
    }
}