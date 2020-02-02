package com.lobach.movielounge.command;

import com.lobach.movielounge.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandManager {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAMETER_COMMAND = "command";

    private CommandManager() {
    }

    public static ActionCommand defineCommand(HttpServletRequest request) throws CommandException {
        String actionString = request.getParameter(PARAMETER_COMMAND);
        try {
            CommandType commandType = CommandType.valueOf(actionString.toUpperCase());
            return commandType.command;
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Undefined command: %s", actionString));
            throw new CommandException(actionString);
        }
    }
}
