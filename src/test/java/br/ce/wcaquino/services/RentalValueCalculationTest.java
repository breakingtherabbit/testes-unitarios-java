package br.ce.wcaquino.services;

import br.ce.wcaquino.entities.Movie;
import br.ce.wcaquino.entities.Rent;
import br.ce.wcaquino.entities.User;
import br.ce.wcaquino.exceptions.MovieWithEmptyInventoryException;
import br.ce.wcaquino.exceptions.RentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class RentalValueCalculationTest {

    private RentService service;

    @Parameter
    public List<Movie> movies;
    @Parameter(value = 1)
    public Double rentalValue;
    @Parameter(value = 2)
    public String message;

    @Before
    public void setUp() {
        service = new RentService();
    }

    private static final Movie movie1 = new Movie("Movie 1", 2, 4.0);
    private static final Movie movie2 = new Movie("Movie 2", 2, 4.0);
    private static final Movie movie3 = new Movie("Movie 3", 2, 4.0);
    private final static Movie movie4 = new Movie("Movie 4", 2, 4.0);
    private final static Movie movie5 = new Movie("Movie 5", 2, 4.0);
    private final static Movie movie6 = new Movie("Movie 6", 2, 4.0);
    private final static Movie movie7 = new Movie("Movie 7", 2, 4.0);

    @Parameters(name = "{2}")
    public static Collection<Object[]> getParams() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(movie1, movie2), 8.0, "2 movies: 0%"},
                {Arrays.asList(movie1, movie2, movie3), 11.0, "3 movies: 25%"},
                {Arrays.asList(movie1, movie2, movie3, movie4), 13.0, "4 movies: 50%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5), 14.0, "5 movies: 75%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6), 14.0, "6 movies: 100%"},
                {Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7), 18.0, "7 movies: 0%"}
        });
    }

    @Test
    public void shouldCalculatesRentalValueWithDiscount() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = new User("User 1");

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        assertThat(rent.getValue(), is(rentalValue));
    }

}
