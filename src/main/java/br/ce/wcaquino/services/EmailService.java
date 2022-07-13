package br.ce.wcaquino.services;

import br.ce.wcaquino.entities.User;

public interface EmailService {

    void notifyOverdue(User user);

}
