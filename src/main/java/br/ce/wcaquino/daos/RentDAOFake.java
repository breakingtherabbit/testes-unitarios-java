package br.ce.wcaquino.daos;

import br.ce.wcaquino.entities.Rent;

import java.util.Collections;
import java.util.List;

public class RentDAOFake implements RentDAO {

    @Override
    public void save(Rent rent) {
        // Abstraction to save
    }

    @Override
    public List<Rent> getPending() {
        return Collections.emptyList();
    }

}
