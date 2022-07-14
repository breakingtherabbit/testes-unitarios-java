package br.ce.wcaquino.services;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorMockTest {

    @Test
    @Ignore("Just for reference")
    public void teste() {
        Calculator calculator = mock(Calculator.class);
        when(calculator.sum(eq(1), anyInt())).thenReturn(5);

        System.out.println(calculator.sum(2, 2));

        fail();
    }
}
