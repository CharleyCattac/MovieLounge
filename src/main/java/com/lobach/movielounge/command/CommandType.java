package com.lobach.movielounge.command;

import com.lobach.movielounge.command.impl.*;

import java.util.logging.Logger;

public enum CommandType {
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_PAGE(new ChangePageCommand()),

    SHOW_MOVIES(new ShowMoviesCommand()),
    CREATE_MOVIE(new CreateMovieCommand()),
    DELETE_MOVIE(new DeleteMovieCommand()),

    SHOW_EVENTS(new ShowEventsCommand()),
    MAKE_BOOKING(new MakeBookingCommand()),
    CREATE_EVENT(new CreateEventCommand()),
    DELETE_EVENT(new DeleteEventCommand()),

    SHOW_REVIEWS(new ShowReviewsCommand()),

    OPEN_PROFILE(new OpenProfileCommand()),

    SIGN_UP(new SignUpCommand()),
    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand());

    public final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }
}
