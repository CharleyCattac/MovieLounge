package com.lobach.movielounge.command;

import com.lobach.movielounge.model.UserRole;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Action command interface.
 *
 * @author Renata Lobach
 */

@FunctionalInterface
public interface ActionCommand {
    Logger logger = LogManager.getLogger();

    String ATTRIBUTE_LOCALE = "locale";
    String ATTRIBUTE_ROLE = "userRole";

    /**
     * This method extracts necessary data from
     * @param content and performs the requested action,
     * then it puts the results back into {@param content};
     *
     * @return {@code Router} object, that contains info about next request
     * (response/redirect and page).
     */
    Router execute(RequestContent content);

    /**
     * This method extracts locale attribute from session;
     *
     * @return {@code LocaleType} object, which is used to define locale
     * for responding messages.
     */
    default LocaleType defineLocale(RequestContent content) {
        String locale = (String) content.getSessionAttribute(ATTRIBUTE_LOCALE);
        return LocaleType.valueOf(locale.toUpperCase());
    }

    /**
     * This method checks if role from session is the one required to perform certain command;
     *
     * @return true if roles match, false if they don't.
     */
    default boolean rolesMatch(RequestContent content, UserRole expectedRole) {
        UserRole actualRole = (UserRole) content.getSessionAttribute(ATTRIBUTE_ROLE);
        return actualRole.equals(expectedRole);
    }
}
