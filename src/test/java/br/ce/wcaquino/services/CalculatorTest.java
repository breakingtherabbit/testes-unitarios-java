package br.ce.wcaquino.services;

import br.ce.wcaquino.exceptions.CantDivideByZeroException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void deveSomarDoisValores() {
        // cenario
        int a = 5;
        int b = 3;

        // acao
        int resultado = calculator.sum(a, b);

        // verificacao
        assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        // cenario
        int a = 8;
        int b = 5;

        // acao
        int resultado = calculator.sub(a, b);

        // verificacao
        assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws CantDivideByZeroException {
        // cenario
        int a = 6;
        int b = 3;

        // acao
        int resultado = calculator.divide(a, b);

        // verificacao
        assertEquals(2, resultado);
    }

    @Test(expected = CantDivideByZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws CantDivideByZeroException {
        // cenario
        int a = 10;
        int b = 0;

        // acao
        calculator.divide(a, b);
    }
}
