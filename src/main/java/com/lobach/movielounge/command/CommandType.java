package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.*;

public enum CommandType {
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_PAGE(new ChangePageCommand()),

    SHOW_MOVIES(new ShowMoviesCommand()),

    SHOW_EVENTS(new ShowEventsCommand()),
    DELETE_EVENT(new DeleteEventCommand()),

    SHOW_REVIEWS(new ShowReviewsCommand()),

    LOG_IN(new LogInCommand());

    public final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }
}
