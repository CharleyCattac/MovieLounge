package com.lobach.movielounge.model.entity;

import java.util.Objects;

public class Review {
    private long id;
    private User user;
    private Movie movie;
    private Integer rating;
    private String reviewText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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
                Objects.equals(user, review.user) &&
                Objects.equals(movie, review.movie) &&
                Objects.equals(rating, review.rating) &&
                Objects.equals(reviewText, review.reviewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, movie, rating, reviewText);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", movie=" + movie +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
