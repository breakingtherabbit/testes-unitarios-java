package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        // cenario
        int a = 5;
        int b = 3;

        // acao
        int resultado = calculadora.somar(a, b);

        // verificacao
        assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        // cenario
        int a = 8;
        int b = 5;

        // acao
        int resultado = calculadora.subtrair(a, b);

        // verificacao
        assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        // cenario
        int a = 6;
        int b = 3;

        // acao
        int resultado = calculadora.dividir(a, b);

        // verificacao
        assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        // cenario
        int a = 10;
        int b = 0;

        // acao
        calculadora.dividir(a, b);
    }
}
