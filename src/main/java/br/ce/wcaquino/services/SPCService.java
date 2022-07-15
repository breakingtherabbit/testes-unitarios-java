package br.ce.wcaquino.services;

import br.ce.wcaquino.entities.User;

public interface SPCService {

    boolean haveDebt(User user) throws Exception;

}
