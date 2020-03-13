package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.EmptyCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The Command manager class.
 *
 * @author Renata Lobach
 * @since 2020-02-01
 */

public class CommandManager {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAMETER_COMMAND = "command";

    /**
     * The private class constructor to hide the implicit public one.
     */
    private CommandManager() {
    }

    /**
     * This method defines which command was requested
     *
     * @param request - http request, contains @command parameter;
     * @return the encoded string.
     */
    public static ActionCommand defineCommand(HttpServletRequest request) {
        String actionString = request.getParameter(PARAMETER_COMMAND);
        try {
            CommandType commandType = CommandType.valueOf(actionString.toUpperCase());
            return commandType.command;
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Undefined command: %s", actionString));
            return new EmptyCommand();
        }
    }
}
