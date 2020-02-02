package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.ChangeLocaleCommand;
import com.lobach.movielounge.command.impl.RegisterCommand;

public enum CommandType {
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    REGISTER(new RegisterCommand());

    public final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }
}
