package br.ce.wcaquino.services;

import br.ce.wcaquino.entities.Movie;
import br.ce.wcaquino.entities.Rent;
import br.ce.wcaquino.entities.User;
import br.ce.wcaquino.exceptions.MovieWithEmptyInventoryException;
import br.ce.wcaquino.exceptions.RentException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.matchers.MyMatchers.*;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static java.util.Calendar.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class RentServiceTest {

    private RentService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new RentService();
    }

    @Test
    public void shouldRentAMovie() throws Exception {
//        assumeFalse(DataUtils.verificarDiaSemana(new Date(), SATURDAY));

        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = List.of(new Movie("Movie 1", 2, 5.0));

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        error.checkThat(rent.getValue(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(rent.getRentDate(), new Date()), is(true));
        error.checkThat(rent.getRentDate(), itsToday());
        error.checkThat(isMesmaData(rent.getDevolutionDate(), obterDataComDiferencaDias(1)), is(true));
        error.checkThat(rent.getDevolutionDate(), itsTodayWithDifferenceBetweenDays(1));
    }

    @Test(expected = MovieWithEmptyInventoryException.class)
    public void shouldThrowsExceptionWhenRentAMovieWithEmptyInventory() throws Exception {
        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = List.of(new Movie("Movie 1", 0, 5.0));

        // WHEN
        service.rentMovie(user, movies);
    }

    @Test
    public void shouldThrowsExceptionWhenRentAMovieWithEmptyUser() throws MovieWithEmptyInventoryException {
        // GIVEN
        List<Movie> movies = List.of(new Movie("Movie 2", 1, 4.0));

        // WHEN
        try {
            service.rentMovie(null, movies);
            Assert.fail();
        } catch (RentException e) {
            assertThat(e.getMessage(), is("Empty user"));
        }
    }

    @Test
    public void shouldThrowsExceptionWhenRentAMovieWithoutMovies() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = new User("User 1");

        exception.expect(RentException.class);
        exception.expectMessage("Empty movie");

        // WHEN
        service.rentMovie(user, null);
    }

    @Test
    public void shouldRentTwoMovies() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 5.0), new Movie("Movie 2", 1, 5.0));

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        assertThat(rent.getMovies().size(), is(equalTo(2)));
    }

    @Test
    public void shouldDevolvesInTheMondayWhenRentMovieInSaturday() throws MovieWithEmptyInventoryException, RentException {
        assumeTrue(DataUtils.verificarDiaSemana(new Date(), SATURDAY));

        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = List.of(new Movie("Movie 1", 1, 5.0));

        // WHEN
        Rent rental = service.rentMovie(user, movies);

        // THEN
        boolean isMonday = DataUtils.verificarDiaSemana(rental.getDevolutionDate(), MONDAY);
        assertTrue(isMonday);
        assertThat(rental.getDevolutionDate(), itsOn(SUNDAY));
        assertThat(rental.getDevolutionDate(), itsOnMonday());
    }

    @Test
    @Ignore("Just for reference JUnit 4")
    public void shouldBeIgnored() {
        Assert.fail();
    }
}
