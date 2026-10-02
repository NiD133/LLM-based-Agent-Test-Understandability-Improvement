package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test06 extends JulianChronology_ESTest_scaffolding {

    /**
     * In the Julian calendar a proleptic year is a leap year when it is exactly
     * divisible by four. The year -11,999,988 is a multiple of four
     * (-11,999,988 / 4 = -2,999,997), so it must be reported as a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsTrue_forYearDivisibleByFour() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        boolean isLeapYear = julianChronology.isLeapYear(-11999988L);

        assertTrue("A year divisible by four should be a Julian leap year", isLeapYear);
    }
}
