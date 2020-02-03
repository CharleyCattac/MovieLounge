package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.*;

public enum CommandType {
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SHOW_MOVIES(new ShowMoviesCommand());

    public final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }
}
