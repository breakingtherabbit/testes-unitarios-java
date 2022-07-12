package br.ce.wcaquino.suites;

import br.ce.wcaquino.services.CalculatorTest;
import br.ce.wcaquino.services.RentServiceTest;
import br.ce.wcaquino.services.RentalValueCalculationTest;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculatorTest.class,
        RentalValueCalculationTest.class,
        RentServiceTest.class
})
@Ignore
public class SuiteExecution {
    // Remova se puder!
}
