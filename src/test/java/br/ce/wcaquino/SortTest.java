package br.ce.wcaquino;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortTest {

    public static int counter = 0;

    @Test
    public void testStart() {
        counter = 1;
    }

    @Test
    public void testVerify() {
        Assert.assertEquals(1, counter);
    }
}
