package br.ce.wcaquino.exceptions;

import java.io.Serial;

public class LocadoraException extends Exception {

    @Serial
    private static final long serialVersionUID = 3837982804180390821L;

    public LocadoraException(String message) {
        super(message);
    }
}
