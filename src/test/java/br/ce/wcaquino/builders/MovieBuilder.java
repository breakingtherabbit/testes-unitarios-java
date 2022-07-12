package br.ce.wcaquino.builders;

import br.ce.wcaquino.entities.Movie;

public class MovieBuilder {

    private Movie movie;

    private MovieBuilder() {}

    public static MovieBuilder oneMovie() {
        MovieBuilder builder = new MovieBuilder();
        builder.movie = new Movie();
        builder.movie.setStock(2);
        builder.movie.setName("Movie 1");
        builder.movie.setPriceRent(4.0);
        return builder;
    }

    /*
    Outra forma de construir um builder
     */
    public static MovieBuilder oneMovieOutOfStock() {
        MovieBuilder builder = new MovieBuilder();
        builder.movie = new Movie();
        builder.movie.setStock(0);
        builder.movie.setName("Movie 1");
        builder.movie.setPriceRent(4.0);
        return builder;
    }

    public MovieBuilder outOfStock() {
        movie.setStock(0);
        return this;
    }

    public MovieBuilder withPrice(Double price) {
        movie.setPriceRent(price);
        return this;
    }

    public Movie now() {
        return movie;
    }
}
