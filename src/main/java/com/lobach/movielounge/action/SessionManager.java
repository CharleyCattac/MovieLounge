package com.lobach.movielounge.action;

import com.lobach.movielounge.entity.Session;

import java.util.Date;

public class SessionManager {
    public static final int MAX_BOOKING_AMOUNT = 20;

    public static boolean checkIfOutdated(Session session) {
        Date currentDate = new Date();
        boolean isOutdated = currentDate.after(session.getDate());
        session.setAvailable(isOutdated);
        return isOutdated;
    }

    public static boolean checkIfFull(Session session) {
        session.setAvailable(session.getBooking_amount() < MAX_BOOKING_AMOUNT);
        boolean isFull = session.getBooking_amount() < MAX_BOOKING_AMOUNT;
        session.setAvailable(isFull);
        return isFull;
    }
}
