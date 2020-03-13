package com.lobach.movielounge.model;

public enum ReviewSupplier {
    INSTANCE; // TODO: 06/02/2020

    public Review createBasic(long userId, long movieEventId, int rate,
                       String shortOverall, String reviewText) {
        Review review = new Review();
        review.setUserId(userId);
        review.setMovieEventId(movieEventId);
        review.setRate(rate);
        review.setShortOverallText(shortOverall);
        review.setReviewText(reviewText);
        return review;
    }

    public Review createFull(long id, long userId, long movieEventId, int rate,
                       String shortOverall, String reviewText) {
        Review review = this.createBasic(userId, movieEventId, rate, shortOverall, reviewText);
        review.setId(id);
        return review;
    }
}
