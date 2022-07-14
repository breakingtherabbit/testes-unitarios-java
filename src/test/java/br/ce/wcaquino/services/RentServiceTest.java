package br.ce.wcaquino.services;

import br.ce.wcaquino.daos.RentDAO;
import br.ce.wcaquino.entities.Movie;
import br.ce.wcaquino.entities.Rent;
import br.ce.wcaquino.entities.User;
import br.ce.wcaquino.exceptions.MovieWithEmptyInventoryException;
import br.ce.wcaquino.exceptions.RentException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.MovieBuilder.oneMovie;
import static br.ce.wcaquino.builders.RentBuilder.oneRent;
import static br.ce.wcaquino.builders.UserBuilder.oneUser;
import static br.ce.wcaquino.matchers.MyMatchers.*;
import static br.ce.wcaquino.utils.DataUtils.*;
import static java.util.Calendar.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RentServiceTest {

    @Mock private RentDAO rentDAO;
    @Mock private SPCService spcService;
    @Mock private EmailService emailService;
    @InjectMocks private RentService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldRentAMovie() throws Exception {
        // GIVEN
        User user = oneUser().now();
        List<Movie> movies = List.of(oneMovie().withPrice(5.0).now());

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
        User user = oneUser().now();
        List<Movie> movies = List.of(oneMovie().outOfStock().now());

        // WHEN
        service.rentMovie(user, movies);
    }

    @Test
    public void shouldThrowsExceptionWhenRentAMovieWithEmptyUser() throws MovieWithEmptyInventoryException {
        // GIVEN
        List<Movie> movies = List.of(oneMovie().now());

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
        User user = oneUser().now();

        exception.expect(RentException.class);
        exception.expectMessage("Empty movie");

        // WHEN
        service.rentMovie(user, null);
    }

    @Test
    public void shouldRentTwoMovies() throws MovieWithEmptyInventoryException, RentException {
        // GIVEN
        User user = oneUser().now();
        List<Movie> movies = Arrays.asList(oneMovie().now(), oneMovie().now());

        // WHEN
        Rent rent = service.rentMovie(user, movies);

        // THEN
        assertThat(rent.getMovies().size(), is(equalTo(2)));
    }

    @Test
    public void shouldDevolvesInTheMondayWhenRentMovieInSaturday() throws MovieWithEmptyInventoryException, RentException {
        assumeTrue(verificarDiaSemana(new Date(), SATURDAY));

        // GIVEN
        User user = oneUser().now();
        List<Movie> movies = List.of(oneMovie().now());

        // WHEN
        Rent rental = service.rentMovie(user, movies);

        // THEN
        boolean isMonday = verificarDiaSemana(rental.getDevolutionDate(), MONDAY);
        assertTrue(isMonday);
        assertThat(rental.getDevolutionDate(), itsOn(SUNDAY));
        assertThat(rental.getDevolutionDate(), itsOnMonday());
    }

    @Test
    @Ignore("Just for reference JUnit 4")
    public void shouldBeIgnored() {
        Assert.fail();
    }

    @Test
    public void shouldNotRentAMovieToThoseWhoHaveDebt() throws MovieWithEmptyInventoryException {
        // GIVEN
        User user = oneUser().now();
        List<Movie> movies = List.of(oneMovie().now());

        when(spcService.haveDebt(any(User.class))).thenReturn(true);

        // WHEN
        try {
            service.rentMovie(user, movies);

        // THEN
            fail();
        } catch (RentException e) {
            assertThat(e.getMessage(), is("User has debt"));
        }

        verify(spcService).haveDebt(user);
    }

    @Test
    public void shouldSendEmailToLatecomers() {
        // GIVEN
        User user = oneUser().withName("Latecomer 1").now();
        User user2 = oneUser().withName("OK").now();
        User user3 = oneUser().withName("Latecomer 2").now();
        List<Rent> rents = List.of(
                oneRent().overdue().withUser(user).now(),
                oneRent().withUser(user2).now(),
                oneRent().overdue().withUser(user3).now(),
                oneRent().overdue().withUser(user3).now()
        );
        when(rentDAO.getPending()).thenReturn(rents);

        // WHEN
        service.notifyOverdue();

        // THEN
        verify(emailService, times(3)).notifyOverdue(any(User.class));
        verify(emailService).notifyOverdue(user);
        verify(emailService, atLeastOnce()).notifyOverdue(user3);
        verify(emailService, never()).notifyOverdue(user2);
        verifyNoMoreInteractions(emailService);
        verifyZeroInteractions(spcService); // Just for reference
    }
}
