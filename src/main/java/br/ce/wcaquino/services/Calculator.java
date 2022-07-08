package br.ce.wcaquino.services;

import br.ce.wcaquino.exceptions.CantDivideByZeroException;

public class Calculator {

    public int sum(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) throws CantDivideByZeroException {
        if (b == 0) {
            throw new CantDivideByZeroException();
        }
        return a / b;
    }
}
