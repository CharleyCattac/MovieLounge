package com.lobach.movielounge.model.validator;

import com.lobach.movielounge.model.entity.MovieSession;
import com.lobach.movielounge.model.entity.Reservation;

public class ReservationValidator {
    private MovieSessionValidator sessionValidator;
    private String message;

    public ReservationValidator() {
        sessionValidator = MovieSessionValidator.INSTANCE;
        message = "";
    }

    public boolean validate(Reservation reservation) {
        MovieSession movieSession = reservation.getMovieSession();
        if (!sessionValidator.validate(movieSession)) {
            message = sessionValidator.getMessage();
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }
}
