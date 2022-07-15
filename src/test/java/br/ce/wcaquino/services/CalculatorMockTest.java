package br.ce.wcaquino.services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorMockTest {

    @Test
    public void teste() {
        Calculator calculator = mock(Calculator.class);
        when(calculator.sum(eq(1), anyInt())).thenReturn(5);

        assertEquals(5, calculator.sum(1, 2));
    }
}
