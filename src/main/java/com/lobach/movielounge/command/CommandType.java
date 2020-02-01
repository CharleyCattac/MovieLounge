package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.RegisterCommand;

public enum CommandType {
    REGISTER(new RegisterCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
