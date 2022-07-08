package br.ce.wcaquino.exceptions;

import java.io.Serial;

public class RentException extends Exception {

    @Serial
    private static final long serialVersionUID = 3837982804180390821L;

    public RentException(String message) {
        super(message);
    }
}
