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

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
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
        assumeFalse(DataUtils.verificarDiaSemana(new Date(), SATURDAY));

        // Cenário
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(new Movie("Movie 1", 2, 5.0));

        // Ação
        Rent rent = service.rentMovie(user, movies);

        // Verificação
        error.checkThat(rent.getValue(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(rent.getRentDate(), new Date()), is(true));
        error.checkThat(isMesmaData(rent.getDevolutionDate(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = MovieWithEmptyInventoryException.class)
    public void shouldThrowsExceptionWhenRentAMovieWithEmptyInvetory() throws Exception {
        // Cenário
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(new Movie("Movie 1", 0, 5.0));

        // Ação
        service.rentMovie(user, movies);
    }

    @Test
    public void shouldThrowsExceptionWhenRentAMovieWithEmptyUser() throws MovieWithEmptyInventoryException {
        // Cenário
        List<Movie> movies = Arrays.asList(new Movie("Movie 2", 1, 4.0));

        // Ação
        try {
            service.rentMovie(null, movies);
            Assert.fail();
        } catch (RentException e) {
            assertThat(e.getMessage(), is("Empty user"));
        }
    }

    @Test
    public void shouldThrowsExceptionWhenRentAMovieWithoutMovies() throws MovieWithEmptyInventoryException, RentException {
        // Cenário
        User user = new User("User 1");

        exception.expect(RentException.class);
        exception.expectMessage("Empty movie");

        // Ação
        service.rentMovie(user, null);
    }

    @Test
    public void shouldRentTwoMovies() throws MovieWithEmptyInventoryException, RentException {
        // Cenário
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 5.0), new Movie("Movie 2", 1, 5.0));

        // Ação
        Rent rent = service.rentMovie(user, movies);

        // Verificação
        assertThat(rent.getMovies().size(), is(equalTo(2)));
    }

    @Test
    public void shouldPay75PerCentInTheThirdMovie() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(
                new Movie("Movie 1", 2, 4.0),
                new Movie("Movie 2", 2, 4.0),
                new Movie("Movie 3", 2, 4.0));

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        assertThat(rent.getValue(), is(11.0));
    }

    @Test
    public void shouldPay50PerCentInTheFourthMovie() throws MovieWithEmptyInventoryException, RentException {
        // cenario
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(
                new Movie("Movie 1", 2, 4.0),
                new Movie("Movie 2", 2, 4.0),
                new Movie("Movie 3", 2, 4.0),
                new Movie("Movie 3", 2, 4.0));

        // acao
        Rent rent = service.rentMovie(user, movies);

        // verificacao
        assertThat(rent.getValue(), is(13.0));
    }

    @Test
    public void shouldPay25PerCentInTheFifthMovie() throws MovieWithEmptyInventoryException, RentException {
        // cenario
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(
                new Movie("Movie 1", 2, 4.0),
                new Movie("Movie 2", 2, 4.0),
                new Movie("Movie 3", 2, 4.0),
                new Movie("Movie 4", 2, 4.0),
                new Movie("Movie 5", 2, 4.0));

        // acao
        Rent rent = service.rentMovie(user, movies);

        // verificacao
        assertThat(rent.getValue(), is(14.0));
    }

    @Test
    public void shouldPayZeroPerCentInTheSixthMovie() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(
                new Movie("Movie 1", 2, 4.0),
                new Movie("Movie 2", 2, 4.0),
                new Movie("Movie 3", 2, 4.0),
                new Movie("Movie 4", 2, 4.0),
                new Movie("Movie 5", 2, 4.0),
                new Movie("Movie 6", 2, 4.0));

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        assertThat(rent.getValue(), is(14.0));
    }

    @Test
    public void shouldDevolvesInTheMondayWhenRentMovieInSaturday() throws MovieWithEmptyInventoryException, RentException {
        assumeTrue(DataUtils.verificarDiaSemana(new Date(), SATURDAY));

        // GIVEN
        User user = new User("User 1");
        List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 5.0));

        // WHEN
        Rent retorno = service.rentMovie(user, movies);

        // THEN
        boolean isMonday = DataUtils.verificarDiaSemana(retorno.getRentDate(), MONDAY);
        assertTrue(isMonday);
    }

    @Test
    @Ignore("Just for reference JUnit 4")
    public void shouldBeIgnored() {
        Assert.fail();
    }
}
