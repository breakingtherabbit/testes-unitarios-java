package br.ce.wcaquino.daos;

import br.ce.wcaquino.entities.Rent;

import java.util.List;

public interface RentDAO {

    void save(Rent rent);

    List<Rent> getPending();

}
