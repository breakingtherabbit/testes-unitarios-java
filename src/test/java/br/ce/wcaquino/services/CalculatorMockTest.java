package br.ce.wcaquino.services;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorMockTest {

    @Test
    public void teste() {
        Calculator calculator = mock(Calculator.class);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        when(calculator.sum(captor.capture(), captor.capture())).thenReturn(5);

        assertEquals(5, calculator.sum(1, 2));
        System.out.println(captor.getAllValues());
    }
}
