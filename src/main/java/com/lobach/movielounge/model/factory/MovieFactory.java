package com.lobach.movielounge.model.factory;

import com.lobach.movielounge.model.entity.Movie;

public enum MovieFactory {
    INSTANCE;

    MovieFactory() {
    }

    public Movie create(String title, String description, int releaseYear, String director, float rating) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setRating(rating);
        return movie;
    }

    public Movie createFull (long id, String title, String description, int releaseYear, String director, float rating) {
        Movie movie = this.create(title, description, releaseYear, director, rating);
        movie.setId(id);
        return movie;
    }
}
