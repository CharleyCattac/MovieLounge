package com.lobach.movielounge.model;

import java.util.Objects;

public class Review {
    private long id;
    private long userId;
    private long movieEventId;
    private User user;
    private MovieEvent movieEvent;
    private int rating;
    private String shortOverallText;
    private String reviewText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieEventId() {
        return movieEventId;
    }

    public void setMovieEventId(long movieEventId) {
        this.movieEventId = movieEventId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MovieEvent getMovieEvent() {
        return movieEvent;
    }

    public void setMovieEvent(MovieEvent movieEvent) {
        this.movieEvent = movieEvent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getShortOverallText() {
        return shortOverallText;
    }

    public void setShortOverallText(String shortOverallText) {
        this.shortOverallText = shortOverallText;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                userId == review.userId &&
                movieEventId == review.movieEventId &&
                rating == review.rating &&
                Objects.equals(user, review.user) &&
                Objects.equals(movieEvent, review.movieEvent) &&
                Objects.equals(shortOverallText, review.shortOverallText) &&
                Objects.equals(reviewText, review.reviewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieEventId, user, movieEvent, rating, shortOverallText, reviewText);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieEventId=" + movieEventId +
                ", user=" + user +
                ", movieEvent=" + movieEvent +
                ", rating=" + rating +
                ", shortOverallText='" + shortOverallText + '\'' +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
