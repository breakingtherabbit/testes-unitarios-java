package br.ce.wcaquino.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorMockTest {

    @Mock
    private Calculator calculatorMock;
    @Spy
    private Calculator calculatorSpy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldShowDifferencesBetweenMockAndSpy() {
        when(calculatorMock.sum(1, 2)).thenReturn(5);
//        when(calculatorSpy.sum(1, 2)).thenReturn(5);
        doReturn(5).when(calculatorSpy).sum(1, 2);
        doNothing().when(calculatorSpy).print();

        System.out.println("MOCK:" + calculatorMock.sum(1, 2));
        System.out.println("SPY:" + calculatorSpy.sum(1, 2));

        System.out.println("Mock");
        calculatorMock.print();
        System.out.println("Spy");
        calculatorSpy.print();
    }

    @Test
    public void teste() {
        Calculator calculator = mock(Calculator.class);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        when(calculator.sum(captor.capture(), captor.capture())).thenReturn(5);

        assertEquals(5, calculator.sum(1, 2));
        System.out.println(captor.getAllValues());
    }
}
