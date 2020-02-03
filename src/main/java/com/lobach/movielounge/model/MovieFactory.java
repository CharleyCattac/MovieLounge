package com.lobach.movielounge.model;

public enum MovieFactory {
    INSTANCE;

    MovieFactory() {
    }

    public Movie create(String title, String description, String posterUrl,
                        int releaseYear, String director, float rating) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setRating(rating);
        return movie;
    }

    public Movie createWithId(long id, String title, String description, String posterUrl,
                              int releaseYear, String director, float rating) {
        Movie movie = this.create(title, description, posterUrl, releaseYear, director, rating);
        movie.setId(id);
        return movie;
    }
}
