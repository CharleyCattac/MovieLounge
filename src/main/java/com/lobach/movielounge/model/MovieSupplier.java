package com.lobach.movielounge.model;

public enum MovieSupplier {
    INSTANCE;

    MovieSupplier() {
    }

    public Movie createBasic(String title, String description, String posterUrl,
                             int releaseYear, String director, float rating) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setPoster(posterUrl);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setRating(rating);
        return movie;
    }

    public Movie createFull(long id, String title, String description, String posterUrl,
                            int releaseYear, String director, float rating) {
        Movie movie = this.createBasic(title, description, posterUrl, releaseYear, director, rating);
        movie.setId(id);
        return movie;
    }
}
