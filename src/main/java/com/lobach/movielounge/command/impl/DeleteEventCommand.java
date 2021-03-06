package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

public class DeleteEventCommand implements ActionCommand {
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_ACCESS_MESSAGE = "error.invalid_access";
    private static final String PROPERTY_DEFAULT_ERROR_MESSAGE = "error.default";

    private static final String PARAMETER_EVENT_ID = "event_id";

    private static final String ATTRIBUTE_ERROR_EVENT_ID = "errorEventId";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";

    private static final UserRole EXPECTED_ROLE = UserRole.ADMIN;

    private MovieEventService movieEventService;
    private ShowEventsCommand showEventsCommand;

    public DeleteEventCommand() {
        movieEventService = new MovieEventServiceImpl();
        showEventsCommand = new ShowEventsCommand();
    }

    @Override
    public Router execute(RequestContent content) {
        LocaleType localeType = defineLocale(content);

        if (!rolesMatch(content, EXPECTED_ROLE)) {
            String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ACCESS_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            logger.error("Failed to delete event because of invalid access rights.");
        } else {
            long eventId = Long.parseLong(content.getRequestParameter(PARAMETER_EVENT_ID));
            try {
                movieEventService.deleteEventById(eventId);
            } catch (ServiceException e) {
                String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_DEFAULT_ERROR_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, defaultErrorMessage);
                content.setRequestAttribute(ATTRIBUTE_ERROR_EVENT_ID, eventId);
                logger.error(String.format("Failed to delete event %d: ", eventId), e);
            }
        }

        return showEventsCommand.execute(content);
    }
}
