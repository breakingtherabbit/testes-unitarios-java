package br.ce.wcaquino.services;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.daos.RentDAO;
import br.ce.wcaquino.entities.Movie;
import br.ce.wcaquino.entities.Rent;
import br.ce.wcaquino.entities.User;
import br.ce.wcaquino.exceptions.MovieWithEmptyInventoryException;
import br.ce.wcaquino.exceptions.RentException;
import br.ce.wcaquino.utils.DataUtils;

import static br.ce.wcaquino.utils.DataUtils.*;
import static java.util.Calendar.SUNDAY;

public class RentService {

    private RentDAO rentDAO;
    private SPCService spcService;
    private EmailService emailService;

    public Rent rentMovie(User user, List<Movie> movies)
            throws MovieWithEmptyInventoryException, RentException {

        if (user == null) {
            throw new RentException("Empty user");
        }

        if (movies == null) {
            throw new RentException("Empty movie");
        }

        for (Movie movie : movies) {
            if (movie.getStock() == 0) {
                throw new MovieWithEmptyInventoryException();
            }
        }

        if (spcService.haveDebt(user)) {
            throw new RentException("User has debt");
        }

        Rent rent = new Rent();
        rent.setMovies(movies);
        rent.setUser(user);
        rent.setRentDate(new Date());

        Double rentPrice = 0D;
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Double moviePrice = movie.getPriceRent();

            switch (i) {
                case 2: moviePrice *= .75; break;
                case 3: moviePrice *= .5; break;
                case 4: moviePrice *= .25; break;
                case 5: moviePrice *= 0; break;
                default: break;
            }

            rentPrice += moviePrice;
        }
        rent.setValue(rentPrice);

        Date dateDevolution = new Date();
        dateDevolution = adicionarDias(dateDevolution, 1);
        if (DataUtils.verificarDiaSemana(dateDevolution, SUNDAY)) {
            dateDevolution = adicionarDias(dateDevolution, 1);
        }

        rent.setDevolutionDate(dateDevolution);

        rentDAO.save(rent);

        return rent;
    }

    public void notifyOverdue() {
        List<Rent> rents = rentDAO.getPending();

        for (Rent rent: rents) {
            emailService.notifyOverdue(rent.getUser());
        }
    }

    public void setRentDAO(RentDAO rentDAO) {
        this.rentDAO = rentDAO;
    }

    public void setSpcService(SPCService spcService) {
        this.spcService = spcService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}