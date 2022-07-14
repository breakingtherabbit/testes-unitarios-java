package br.ce.wcaquino.builders;

import br.ce.wcaquino.entities.Rent;
import br.ce.wcaquino.entities.User;

import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.MovieBuilder.oneMovie;
import static br.ce.wcaquino.builders.UserBuilder.oneUser;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

public class RentBuilder {

    private Rent rent;

    private RentBuilder() {}

    public static RentBuilder oneRent() {
        RentBuilder builder = new RentBuilder();
        builder.rent = new Rent();
        builder.rent.setValue(5.0);
        builder.rent.setRentDate(new Date());
        builder.rent.setDevolutionDate(obterDataComDiferencaDias(1));
        builder.rent.setUser(oneUser().now());
        builder.rent.setMovies(List.of(oneMovie().now()));
        return builder;
    }

    public RentBuilder withDevolutionDate(Date date) {
        rent.setDevolutionDate(date);
        return this;
    }

    public RentBuilder overdue() {
        rent.setRentDate(obterDataComDiferencaDias(-4));
        rent.setDevolutionDate(obterDataComDiferencaDias(-2));
        return this;
    }

    public RentBuilder withUser(User user) {
        rent.setUser(user);
        return this;
    }

    public Rent now() {
        return rent;
    }
}
